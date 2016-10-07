package com.bow.common.pipeline;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

/**
 * @author vv
 * @since 2016/10/4.
 */
public class DefaultClientPipeLine extends StandardPipeline implements ClientPipeline {

    private static DefaultClientPipeLine instance = new DefaultClientPipeLine();

    static {
        instance.add(new ConcurrentLimitClientHandler());
        instance.add(new RouterClientHandler());
        instance.add(new StatisticClientHandler());
        instance.add(new CallableClientHandler());
    }

    @Override
    public Response sendRequest(Request request) {
        if (size > 0) {
            PipelineMessage message = new PipelineMessage();
            message.setRequest(request);
            first.handler.onSendRequest(first, message);
            return message.getResponse();
        }
        return null;
    }

    public static ClientPipeline getInstance() {
        return instance;
    }
}
