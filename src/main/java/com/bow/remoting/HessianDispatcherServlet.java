package com.bow.remoting;

import com.bow.rpc.Request;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;
import com.caucho.hessian.server.HessianServlet;

/**
 * Created by vv on 2016/8/20.
 */
public class HessianDispatcherServlet extends HessianServlet implements HessianCallService{

    private RequestHandler requestHandler;

    @Override
    public Response call(Request message) {
        return requestHandler.handle(message);
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }
}
