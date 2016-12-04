package com.bow.demo.module.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author vv
 * @since 2016/12/2.
 */
public class TestClientOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + "out channel id" + ctx.channel().id() + "  " + msg);
        ctx.write(msg, promise);
    }
}
