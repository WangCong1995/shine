package com.bow.remoting.netty;

import com.bow.common.channel.Channel;
import com.bow.common.channel.NettyChannelPool;
import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.remoting.ShineClient;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import com.bow.rpc.URL;
import io.netty.bootstrap.Bootstrap;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个远端URL对应一个NettyClient实例
 * 
 * @author vv
 * @since 2016/9/1.
 */
public class NettyClient implements ShineClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    /**
     * 此client对应的服务端url
     */
    private URL serverUrl;

    private EventLoopGroup loopGroup = new NioEventLoopGroup();

    private Bootstrap bootstrap;

    /**
     * 连接池
     */
    private NettyChannelPool pool;

    public NettyClient(URL serverUrl) {
        this.serverUrl = serverUrl;
        this.pool = new NettyChannelPool(this);
        initBootstrap();
    }

    /**
     * 通过Channel发送请求
     * 
     * @param request
     *            请求的内容
     * @return Response
     */
    @Override
    public Response call(Request request) {
        Channel channel = null;
        Response response;
        try {
            channel = pool.borrowChannel();
            if (channel == null) {
                throw new ShineException(ShineExceptionCode.connectionException,
                        "pool.borrowChannel() == null, url " + serverUrl);
            }
            response = channel.call(request);
        } catch (Exception e) {
            // 发送异常就将此channel注销
            pool.invalidateChannel(channel);
            throw new ShineException(ShineExceptionCode.connectionException, e);
        }finally {
            pool.returnChannel(channel);
        }
        return response;
    }

    /**
     * initBootstrap
     */
    private void initBootstrap() {
        NettyHelper.setNettyLoggerFactory();
        bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new ObjectEncoder());
                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                p.addLast(new NettyClientHandler());
            }
        });
    }

    public void close() {
        loopGroup.shutdownGracefully();
        LOGGER.info("netty shutdown gracefully");
    }

    public URL getUrl() {
        return serverUrl;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

}
