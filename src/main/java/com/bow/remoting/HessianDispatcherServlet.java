package com.bow.remoting;

import com.bow.rpc.Message;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Result;
import com.caucho.hessian.server.HessianServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vv on 2016/8/20.
 */
public class HessianDispatcherServlet extends HessianServlet implements HessianCallService{

    private RequestHandler requestHandler;

    @Override
    public Result call(Message message) {
        return requestHandler.handle(message);
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }
}
