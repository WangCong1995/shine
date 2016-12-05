package com.bow.common.pipeline;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;

/**
 * 最里层的handler，负责反射调用方法获取结果
 * @author vv
 * @since 2016/12/5.
 */
public class InvokeServiceHandler extends ShineHandlerAdapter {

    private RequestHandler requestHandler;

    public InvokeServiceHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    /**
     * 处理接收请求，并发送响应
     * 
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRequest(ShineHandlerContext context, PipelineMessage message) {
        if (requestHandler == null) {
            throw new ShineException(ShineExceptionCode.fail, "InvokeServiceHandler#requestHandler must not be null");
        }
        Response response = requestHandler.handle(message.getRequest());
        message.setResponse(response);
        //调用前一个handler将响应发送出去
        context.prevSendResponse(message);
    }
}
