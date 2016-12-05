package com.bow.common.pipeline;

/**
 * 默认接连将信息往下传递<br/>
 * 将ShineHandlerContext传给业务handler可以使其能够控制message的流向,
 * 如{@link CallableClientHandler} 接收到响应后
 * 
 * @author vv
 * @since 2016/10/4.
 */
public class ShineHandlerAdapter implements ShineHandler {
    @Override
    public void onSendRequest(ShineHandlerContext context, PipelineMessage message) {
        context.nextSendRequest(message);
    }

    @Override
    public void onReceiveResponse(ShineHandlerContext context, PipelineMessage message) {
        context.prevReceiveResponse(message);
    }

    @Override
    public void onReceiveRequest(ShineHandlerContext context, PipelineMessage message) {
        context.nextReceiveRequest(message);
    }

    @Override
    public void onSendResponse(ShineHandlerContext context, PipelineMessage message) {
        context.prevSendResponse(message);
    }

    @Override
    public void onCatchException(ShineHandlerContext context, PipelineMessage message) {
        context.prevCatchException(message);
    }
}
