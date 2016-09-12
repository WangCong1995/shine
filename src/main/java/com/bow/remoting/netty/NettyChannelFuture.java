package com.bow.remoting.netty;

import com.bow.remoting.DefaultFuture;
import com.bow.remoting.ShineFutureListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author vv
 * @since 2016/9/8.
 */
public class NettyChannelFuture extends DefaultFuture {
    /**
     * client 发送请求后，会调用ShineFuture#get()等待结果返回
     */
    private static final ConcurrentMap<Long, NettyChannelFuture> SYNC_FUTURE_MAP = new ConcurrentHashMap<Long, NettyChannelFuture>();

    public NettyChannelFuture(final long messageId) {
        SYNC_FUTURE_MAP.put(messageId, this);
        this.addFutureListener(new ShineFutureListener() {
            @Override
            public void onSignalComplete() {
                SYNC_FUTURE_MAP.remove(messageId);
            }
        });
    }

    public static NettyChannelFuture getFuture(long messageId) {
        return SYNC_FUTURE_MAP.get(messageId);
    }
}
