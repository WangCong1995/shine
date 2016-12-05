package com.bow.remoting.netty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.executor.ShineExecutors;
import com.bow.common.pipeline.DefaultServerPipeline;
import com.bow.common.pipeline.DefaultShinePipeline;
import com.bow.remoting.ShineServer;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NettyServerHandler
 * @author vv
 * @since 2016/9/3.
 */
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    private ShineServer server;

    public NettyServerHandler(ShineServer server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info(" channelActive id " + ctx.channel().id().asShortText());
    }

    /**
     * 接收请求
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof Request) {
            Request request = (Request) msg;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("channel id: " + ctx.channel().id().asShortText() + " receive\n" + request);
            }
            if (server == null) {
                throw new ShineException(ShineExceptionCode.fail, "server must not be null");
            }
            ShineExecutors.getBizPool().submit(new BizTask(ctx.channel(), request));
        }
    }

    class BizTask implements Runnable{
        private Channel channel;
        private Request request;

        public BizTask(Channel channel, Request request){
            this.channel = channel;
            this.request = request;
        }

        @Override
        public void run() {
            Response response = DefaultServerPipeline.getInstance().receiveRequest(request);
            channel.writeAndFlush(response);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("channel id: " + channel.id().asShortText() + " reply\n" + response);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 在进入下个handler前调用ctx.flush()
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("channel id " + ctx.channel().id().asShortText(), cause);
        ctx.close();
    }
}
