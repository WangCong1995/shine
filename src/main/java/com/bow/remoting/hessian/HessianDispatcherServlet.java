package com.bow.remoting.hessian;

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

    private ShineServer server;

    public HessianDispatcherServlet(ShineServer server){
        this.server = server;
    }

    @Override
    public Response call(Request request) {
        return server.reply(request);
    }
}
