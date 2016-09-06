package com.bow.remoting.netty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.executor.NamedThreadFactory;
import com.bow.config.ShineConfig;
import com.bow.remoting.ShineClient;
import com.bow.rpc.Message;
import com.bow.rpc.Result;
import com.bow.rpc.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by vv on 2016/9/1.
 */
public class NettyClient implements ShineClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static final boolean SSL = false;
    private static final int RECONNECT_INTERVAL_MILLISECONDS = 2000;

    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap;
    private Channel clientChannel;
    private Timer timer;

    public NettyClient(){
        timer = new HashedWheelTimer();
    }


    @Override
    public Result call(URL url, Message message) {
        try {
            connect(url.getHost(),url.getPort());
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail,e);
        }
        sendMessage(message);
        return null;
    }

    public void connect(final String host, final int port) throws Exception {
        NettyHelper.setNettyLoggerFactory();

        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                        }
                        p.addLast(new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                new NettyClientHandler());
                    }
                });
        SocketAddress socketAddress = new InetSocketAddress(host,port);
        doConnect(socketAddress);
    }

    private void doConnect(final SocketAddress socketAddress){

        ChannelFuture future = bootstrap.connect(socketAddress);
        try{
            //等10s还连不上就不连了
            boolean result = future.awaitUninterruptibly(10000, TimeUnit.MILLISECONDS);
            if(result && future.isSuccess()){
                clientChannel = future.channel();
                ChannelFuture closeFuture = clientChannel.closeFuture();
                //发现其关闭后，不停的尝试重连
                closeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        timer.newTimeout(new TimerTask() {
                            @Override
                            public void run(Timeout timeout) throws Exception {
                                logger.warn("retry connect to "+socketAddress.toString());
                                doConnect(socketAddress);
                            }
                        },RECONNECT_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }finally {
            if(!isActive()){
                future.cancel(false);
            }
        }
    }


    public boolean isActive() {
        if (clientChannel == null)
            return false;
        return clientChannel.isActive();
    }

    private ChannelFuture sendMessage(final Message message){

        if(clientChannel==null){
            throw new ShineException(ShineExceptionCode.connectionException);
        }
        ChannelFuture future = clientChannel.writeAndFlush(message);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(logger.isDebugEnabled()){
                    logger.debug("success to send message:"+message);
                }
            }
        });
        return future;
    }


    private void close(){
        timer.stop();
        group.shutdownGracefully();
        logger.info("netty shutdown gracefully");
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        try {
            client.connect("127.0.0.1",1099);
            while(true){
                Message m = new Message();
                m.setInterfaceName("IHello");
                m.setMethodName("sayHello");
                m.setParameters(new String[]{"vv"});
                client.sendMessage(m);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
