package com.bow.remoting;

import com.bow.rpc.RequestHandler;

/**
 * Created by vv on 2016/8/21.
 */
public abstract class AbstractShineServer implements ShineServer{

    private RequestHandler requestHandler;
    @Override
    public void setRequestHandler(RequestHandler requestHandler){
        this.requestHandler = requestHandler;
    }
}
