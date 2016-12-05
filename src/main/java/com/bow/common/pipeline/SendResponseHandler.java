package com.bow.common.pipeline;

/**
 * 服务端的第一个handler
 * @author vv
 * @since 2016/12/5.
 */
public class SendResponseHandler extends ShineHandlerAdapter {

    /**
     * 此方法在管道所有方法中，最后一个执行
     * @param context
     * @param message
     */
    @Override
    public void onSendResponse(ShineHandlerContext context, PipelineMessage message) {

    }
}
