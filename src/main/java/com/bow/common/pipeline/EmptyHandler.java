package com.bow.common.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2016/12/5.
 */
public class EmptyHandler extends ShineHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyHandler.class);

    @Override
    public void onReceiveRequest(ShineHandlerContext context, PipelineMessage message) {
        LOGGER.debug("onReceiveRequest");
        super.onReceiveRequest(context, message);
    }

    @Override
    public void onSendResponse(ShineHandlerContext context, PipelineMessage message) {
        LOGGER.debug("onSendResponse");
        super.onSendResponse(context, message);
    }

    @Override
    public void onCatchException(ShineHandlerContext context, PipelineMessage message) {
        LOGGER.debug("onCatchException");
        super.onCatchException(context, message);
    }
}
