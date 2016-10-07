package com.bow.common.pipeline;


/**
 * 默认不停的将信息往下传递
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
    }

    @Override
    public void onSendResponse(ShineHandlerContext context, PipelineMessage message) {
    }

    @Override
    public void onCatchException(ShineHandlerContext context, PipelineMessage message) {
        context.prevCatchException(message);
    }
}
