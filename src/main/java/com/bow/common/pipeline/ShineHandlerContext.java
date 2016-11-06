package com.bow.common.pipeline;

/**
 * 数据结构模块: 每个context对应一个handler,其维持着handler前后链接关系<br />
 * 业务模块: 提供触发下一个handler的方法
 * 
 * @author vv
 * @since 2016/10/4.
 */
public class ShineHandlerContext {
    /**
     * 链表数据结构
     */
    protected ShineHandlerContext prev;

    protected ShineHandlerContext next;

    protected ShineHandler handler;

    public ShineHandlerContext(ShineHandlerContext previous, ShineHandler handler, ShineHandlerContext next) {
        this.prev = previous;
        this.handler = handler;
        this.next = next;
    }

    /*********************
     * 业务模块 *
     *********************/

    /**
     * 客户端管道：从前往后发送请求
     * 
     * @param message message in pipeline
     */
    public void nextSendRequest(PipelineMessage message) {
        ShineHandlerContext nextContext = this.next;
        if (nextContext != null) {
            nextContext.handler.onSendRequest(nextContext, message);
        }
    }

    /**
     * 客户端管道：由后往前传递响应
     * 
     * @param message message in pipeline
     */
    public void prevReceiveResponse(PipelineMessage message) {

        ShineHandlerContext nextContext = this.prev;
        if (nextContext != null) {
            nextContext.handler.onReceiveResponse(nextContext, message);
        }
    }

    /**
     * 当catch到exception后，由后往前传递
     * 
     * @param message message in pipeline
     */
    public void prevCatchException(PipelineMessage message) {
        ShineHandlerContext nextContext = this.prev;
        if (nextContext != null) {
            nextContext.handler.onCatchException(nextContext, message);
        }
    }
}
