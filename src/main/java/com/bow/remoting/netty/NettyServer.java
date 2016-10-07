package com.bow.remoting.netty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.ShineConfig;
import com.bow.remoting.ShineServer;
import com.bow.rpc.RequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty Server
 * Created by vv on 2016/9/3.
 */
public class NettyServer implements ShineServer{

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private static final boolean SSL = false;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private NettyServerHandler nettyServerHandler;

    private Channel serverChannel;

    private void bind(final int port) throws Exception {
        NettyHelper.setNettyLoggerFactory();

        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc()));
                        }
                        p.addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                nettyServerHandler);
                    }
                });

        ChannelFuture future = bootstrap.bind(port);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                logger.info("----- success to start server -----");
            }
        });
        serverChannel = future.channel();
        ChannelFuture closeFuture = serverChannel.closeFuture();
        closeFuture.addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                logger.info("already close netty server channel");
            }
        });
    }


    @Override
    public void setRequestHandler(RequestHandler requestHandler) {
        nettyServerHandler = new NettyServerHandler(requestHandler);
    }

    @Override
    public boolean start() {
        try {
            if(nettyServerHandler==null){
                throw new ShineException("please set requestHandler with NettyServer#setRequestHandler");
            }
            bind(ShineConfig.getServicePort());
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail,e);
        }
        return true;
    }

    @Override
    public boolean stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        if(logger.isInfoEnabled()){
            logger.info("netty server shutdown");
        }
        return true;
    }
}
