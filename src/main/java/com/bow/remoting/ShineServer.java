package com.bow.remoting;

import com.bow.rpc.Request;
import com.bow.rpc.RequestHandlerAware;
import com.bow.rpc.Response;

/**
 * receive & reply client request
 * @author vv
 * @since 2016/8/19
 */
public interface ShineServer extends RequestHandlerAware{

    void start();

    /**
     * 响应客户端请求
     * @param request request
     * @return Response
     */
    Response reply(Request request);

    void stop();

}
