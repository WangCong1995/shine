package com.bow.remoting.netty;

import com.bow.rpc.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2016/9/3.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("channel is activated, id "+ctx.channel().id());
    }

    /**
     * receive response
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof Result){
            Result result = (Result) msg;
            NettyChannelFuture future = NettyChannelFuture.getFuture(result.getId());
            future.receive(result);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("netty channel exception caught",cause);
        ctx.close();
    }
}
