package com.bow.common.pipeline;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

/**
 * 服务端的pipeline
 * 
 * @author vv
 * @since 2016/10/4.
 */
public interface ServerPipeline extends ShinePipeline {
    /**
     * server receive request
     * 
     * @param request
     *            request
     * @return response
     */
    Response receiveRequest(Request request);
}
