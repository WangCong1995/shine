package com.bow.remoting.netty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.remoting.ShineServer;
import com.bow.rpc.Request;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vv on 2016/9/3.
 */
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private ShineServer server;

    public NettyServerHandler(ShineServer server){
        this.server = server;
    }

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

        if(msg instanceof  Request){
            Request request = (Request) msg;
            if(logger.isDebugEnabled()){
                logger.debug("received request:"+request);
            }
            if(server==null){
                throw new ShineException(ShineExceptionCode.fail,"server must not be null");
            }
            Response response = server.reply(request);
            ctx.write(response);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //在进入下个handler前调用ctx.flush()
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server exceptionCaught",cause);
        ctx.close();
    }
}
