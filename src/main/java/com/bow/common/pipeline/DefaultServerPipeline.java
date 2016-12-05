package com.bow.common.pipeline;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

/**
 * 服务端的管道
 * 
 * @author vv
 * @since 2016/12/5.
 */
public class DefaultServerPipeline extends StandardPipeline implements ServerPipeline {
    /**
     * singleton
     */
    private static DefaultServerPipeline instance = new DefaultServerPipeline();
    static {
        instance.addLast(new EmptyHandler());
        //NOTE : 管道的最后一个handler是反射调用服务的，由server添加
    }

    /**
     * server receive request
     *
     * @param request
     *            request
     * @return response
     */
    @Override
    public Response receiveRequest(Request request) {
        if (size > 0) {
            PipelineMessage message = new PipelineMessage();
            message.setRequest(request);
            first.handler.onReceiveRequest(first, message);
            return message.getResponse();
        }
        return null;
    }

    /**
     * get pipeline
     * 
     * @return ServerPipeline
     */
    public static ServerPipeline getInstance() {
        return instance;
    }
}
