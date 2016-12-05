package com.bow.remoting.hessian;

import com.bow.common.pipeline.DefaultServerPipeline;
import com.bow.common.pipeline.DefaultShinePipeline;
import com.bow.remoting.ShineServer;
import com.bow.rpc.Request;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;
import com.caucho.hessian.server.HessianServlet;


/**
 *
 * @author vv
 * @since 2016/8/20.
 */
public class HessianDispatcherServlet extends HessianServlet implements HessianCallService{


    @Override
    public Response call(Request request) {
        return DefaultServerPipeline.getInstance().receiveRequest(request);
    }
}
