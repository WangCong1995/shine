package com.bow.common.pipeline;

import com.bow.common.ExtensionLoader;
import com.bow.config.ShineConfig;
import com.bow.rpc.Protocol;
import com.bow.rpc.Response;

/**
 * @author vv
 * @since 2016/10/4.
 */
public class CallableClientHandler extends ShineHandlerAdapter {
    @Override
    public void onSendRequest(ShineHandlerContext context, PipelineMessage message) {

        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(ShineConfig.getProtocol());
        Response response = protocol.refer(message.getRequest());
        message.setResponse(response);
        context.prevReceiveResponse(message);
    }
}
