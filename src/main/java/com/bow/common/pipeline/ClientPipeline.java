package com.bow.common.pipeline;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

/**
 * 客戶端的管道
 * @author vv
 * @since 2016/10/4.
 */
public interface ClientPipeline extends ShinePipeline {

    /**
     * 通过此方法请求会经过pipeline层层的handler处理
     * @param request
     * @return
     */
    Response sendRequest(Request request);
}
