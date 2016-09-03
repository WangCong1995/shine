package com.bow.remoting.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.ClosedChannelException;

/**
 * Created by vv on 2016/9/1.
 */
public class CloseHandler extends SimpleChannelHandler {
    private static final Logger logger = LoggerFactory.getLogger(CloseHandler.class);
    public CloseHandler() {
    }

    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("Channel disconnect, " + e.getChannel());
        }

        e.getChannel().close();
        super.channelDisconnected(ctx, e);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        Throwable cause = e.getCause();
        if(logger.isErrorEnabled()) {
            logger.error("Exception caught in " + e.getChannel(), e.getCause());
        }

        if(cause instanceof ClosedChannelException) {
            e.getChannel().close();
        }

        super.exceptionCaught(ctx, e);
    }
}
