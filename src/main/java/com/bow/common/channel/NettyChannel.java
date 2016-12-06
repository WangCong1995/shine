package com.bow.common.channel;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.remoting.ShineFuture;
import com.bow.remoting.netty.NettyChannelFuture;
import com.bow.remoting.netty.NettyClient;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 一个client会有多个通道给server发请求<br/>
 * NettyChannel对{@link #rawChannel} 进行了包装，然后放到了连接池{@link NettyChannelPool}中
 * 
 * @author vv
 * @since 2016/12/4.
 */
public class NettyChannel implements Channel {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyChannel.class);

    private io.netty.channel.Channel rawChannel;

    private Bootstrap bootstrap;

    private SocketAddress socketAddress;

    public NettyChannel(Bootstrap bootstrap, InetSocketAddress socketAddress) {
        this.bootstrap = bootstrap;
        this.socketAddress = socketAddress;
    }

    public String id() {
        if (rawChannel != null) {
            return rawChannel.id().asShortText();
        }
        return null;
    }

    public boolean isActive() {
        return rawChannel.isActive();
    }

    /**
     * get Local Address
     *
     * @return InetSocketAddress
     */
    @Override
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) rawChannel.localAddress();
    }

    /**
     * get Remote Address
     *
     * @return InetSocketAddress
     */
    @Override
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) rawChannel.remoteAddress();
    }

    /**
     * 发送请求到服务器
     *
     * @param request
     *            request
     * @return Response
     */
    @Override
    public boolean send(Request request) {
        if (!isActive()) {
            throw new ShineException(ShineExceptionCode.connectionException, this.toString());
        }
        ChannelFuture writeFuture = rawChannel.writeAndFlush(request);
        writeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("channel " + rawChannel.id().asShortText() + " send request:" + request);
                }
            }
        });
        boolean result = writeFuture.awaitUninterruptibly(2, TimeUnit.SECONDS);
        if (!result || !writeFuture.isSuccess()) {
            writeFuture.cancel(true);
        }
        return result;
    }

    /**
     * 打开一个连接
     */
    public void open() {
        ChannelFuture future = bootstrap.connect(socketAddress);
        try {
            // 等着连接上server,再执行下面的
            future.sync();
            this.rawChannel = future.channel();
        } catch (Exception e) {
            // 忽略
            LOGGER.error("fail to open channel " + toString(), e);
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            if (rawChannel != null) {
                rawChannel.close();
            }
        } catch (Exception e) {
            // 忽略掉
            LOGGER.error("fail to close channel " + toString(), e);
        }

    }

    /**
     * channel info
     * 
     * @return info
     */
    @Override
    public String toString() {
        return id() + " to " + getRemoteAddress().toString();
    }
}
