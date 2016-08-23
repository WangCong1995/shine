package com.bow.remoting;

import com.bow.rpc.RequestHandler;

/**
 * 负责接收请求,并从请求中分析出调用的服务名
 * Created by vv on 2016/8/19.
 */
public interface ShineServer {

    boolean start();

    boolean stop();

    void setRequestHandler(RequestHandler requestHandler);

}
