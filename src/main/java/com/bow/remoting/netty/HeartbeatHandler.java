package com.bow.remoting.netty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import io.netty.handler.timeout.IdleState;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 *
 * Created by vv on 2016/9/1.
 */
public class HeartbeatHandler extends IdleStateAwareChannelHandler {
    private static int MAX_WAIT = 10;
    private int count;
    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {

        super.channelIdle(ctx, e);
        if(IdleState.WRITER_IDLE.equals(e.getState())){
            count++;
        }
        if(count==MAX_WAIT){
            e.getChannel().close();
            throw new ShineException(ShineExceptionCode.connectionException);
        }
    }
}
