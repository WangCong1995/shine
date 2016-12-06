package com.bow.remoting.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * HeartbeatHandler
 *
 * @author vv
 * @since 2016/9/1.
 */
public class HeartbeatHandler extends ChannelDuplexHandler {


    /**
     * 解析事件，然后ping服务器
     * @param ctx ctx
     * @param evt evt
     * @throws Exception e
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.ALL_IDLE){
                ctx.channel().writeAndFlush("ping");
            }
        }
    }
}
