package com.bow.common.pipeline;

import com.bow.rpc.Request;

/**
 * 服务端限制service的并发
 * @author vv
 * @since 2016/10/4.
 */
public class ExecutesLimitServerHandler  extends ShineHandlerAdapter{

    @Override
    public void onReceiveRequest(ShineHandlerContext context, PipelineMessage message) {
    }
}
