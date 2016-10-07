package com.bow.remoting.netty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineFuture;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 一个远端URL对应一个NettyClient实例
 * @author vv
 * @since 2016/9/1.
 */
public class NettyClient implements ShineClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static final boolean SSL = false;

    private static final int RECONNECT_INTERVAL_MILLISECONDS = 3000;

    /**
     * 此client对应的服务端url
     */
    private URL serverUrl;

    private EventLoopGroup group = new NioEventLoopGroup();

    private Bootstrap bootstrap;

    private Channel clientChannel;

    private Timer timer;

    public NettyClient(URL url) {
        serverUrl = url;
        timer = new HashedWheelTimer();
        connect(url.getHost(), url.getPort());
    }

    @Override
    public Response call(Request message) {
        if(!isActive()){
            throw new ShineException(ShineExceptionCode.connectionException," server location "+serverUrl);
        }
        ShineFuture<Response> future = sendMessage(message);
        Response result = future.get(2000, TimeUnit.MILLISECONDS);
        return result;
    }

    private void connect(final String host, final int port){
        NettyHelper.setNettyLoggerFactory();

        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            try {
                sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } catch (SSLException e) {
                throw new ShineException(ShineExceptionCode.fail,e);
            }
        } else {
            sslCtx = null;
        }

        bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                if (sslCtx != null) {
                    p.addLast(sslCtx.newHandler(ch.alloc(), host, port));
                }
                p.addLast(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                        new NettyClientHandler());
            }
        });
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        doConnect(socketAddress);
    }

    private void doConnect(final SocketAddress socketAddress) {

        ChannelFuture future = bootstrap.connect(socketAddress);
        try {
            //等着连接上server,再执行下面的
            future.sync();
        } catch (Exception e) {
            //忽略
            //logger.error("Exception when waiting connect to "+ NetUtil.toString(socketAddress),e);
        }
        clientChannel = future.channel();
        ChannelFuture closeFuture = clientChannel.closeFuture();
        // 发现其关闭后，不停的尝试重连,3s 一次
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                timer.newTimeout(new TimerTask() {
                    @Override
                    public void run(Timeout timeout) throws Exception {
                        logger.warn("retry connect to " + socketAddress.toString());
                        doConnect(socketAddress);
                    }
                }, RECONNECT_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
            }
        });
    }

    /**
     * channel is active or not
     * @return boolean
     */
    public boolean isActive() {
        if (clientChannel == null) {
            return false;
        }
        return clientChannel.isActive();
    }

    private ShineFuture sendMessage(final Request request) {

        ChannelFuture future = clientChannel.writeAndFlush(request);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (logger.isDebugEnabled()) {
                    logger.debug("success to send request:" + request);
                }
            }
        });
        ShineFuture shineFuture = new NettyChannelFuture(request.getId());
        return shineFuture;
    }

    private void close() {
        timer.stop();
        group.shutdownGracefully();
        logger.info("netty shutdown gracefully");
    }

}
