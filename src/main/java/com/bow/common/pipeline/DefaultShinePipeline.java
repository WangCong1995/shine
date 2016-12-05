package com.bow.common.pipeline;

/**
 * @author vv
 * @since 2016/12/5.
 */
@Deprecated
public class DefaultShinePipeline {

    private static StandardPipeline clientPipeline = new StandardPipeline();

    private static StandardPipeline serverPipeline = new StandardPipeline();

    private DefaultShinePipeline() {
        // NO-OP
    }

    public static ShinePipeline getClientPipeline() {
        return clientPipeline;
    }

    public static ShinePipeline getServerPipeline() {
        return serverPipeline;
    }
}
