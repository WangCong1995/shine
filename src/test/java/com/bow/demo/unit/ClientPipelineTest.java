package com.bow.demo.unit;

import com.bow.common.pipeline.CallableClientHandler;
import com.bow.common.pipeline.ClientPipeline;
import com.bow.common.pipeline.ConcurrentLimitClientHandler;
import com.bow.common.pipeline.DefaultClientPipeline;
import com.bow.rpc.Request;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/10/5.
 */
public class ClientPipelineTest {

    @Test
    public void pipeline(){
        ClientPipeline pipeline = new DefaultClientPipeline();
        ConcurrentLimitClientHandler concurrentLimitClientHandler = new ConcurrentLimitClientHandler();
        CallableClientHandler callableClientHandler = new CallableClientHandler();
        pipeline.add(concurrentLimitClientHandler);
        pipeline.add(callableClientHandler);
        Request request = new Request();
        pipeline.sendRequest(request);
    }
}
