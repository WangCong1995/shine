package com.bow.common.pipeline;


/**
 * @author vv
 * @since 2016/10/4.
 */
public interface ShineHandler {
    /**
     * 客户端handler需要实现的方法
     * 
     * @param message
     *            message
     */
    void onSendRequest(ShineHandlerContext context, PipelineMessage message);

    /**
     * 客户端handler需要实现的方法
     *
     * @param message
     *            message
     */
    void onReceiveResponse(ShineHandlerContext context, PipelineMessage message);

    /**
     * 服务端handler需要实现的方法
     *
     * @param message
     *            message
     *
     */
    void onReceiveRequest(ShineHandlerContext context, PipelineMessage message);

    /**
     * 服务端handler需要实现的方法
     *
     * @param message
     *            message
     */
    void onSendResponse(ShineHandlerContext context, PipelineMessage message);

    /**
     * onCatchException
     * 
     * @param message
     *            message
     */
    void onCatchException(ShineHandlerContext context, PipelineMessage message);
}
