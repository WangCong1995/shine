package com.bow.remoting.netty;

import com.bow.rpc.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vv on 2016/9/3.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerAdapter.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("server channelActive");
    }

    /**
     * 接收请求
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        System.out.println("interface "+message.getInterfaceName());
        System.out.println("server receive "+msg);
        ctx.write("response");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server exceptionCaught",cause);
        ctx.close();
    }
}
