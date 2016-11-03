package com.bow.demo;

import com.bow.remoting.hessian.HessianServer;
import com.bow.rpc.Request;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * http://192.168.56.1:9000/ 页面上即可显示
 * @author vv
 * @since 2016/11/3.
 */
public class HessianServerTest {

    @Test
    public void start() throws InterruptedException {
        RequestHandler requestHandler = new RequestHandler() {
            @Override
            public Response handle(Request request) {
                Response response = new Response();
//                response.setValue("hello hessian shine");
                fillResponse(request,response);
                return response;
            }
        };
        HessianServer server = new HessianServer();
        server.setRequestHandler(requestHandler);
        server.start();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    private void fillResponse(Request request,Response response){
        Object[] ary = request.getParameters();
        response.setValue(ary[0]);
    }
}
