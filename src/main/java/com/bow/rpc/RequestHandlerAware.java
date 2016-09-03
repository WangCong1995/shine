package com.bow.rpc;

/**
 * RequestHandlerAware make the implementation able to handle the request
 * @author vv
 * @since 2016/9/3.
 */
public interface RequestHandlerAware {
    void setRequestHandler(RequestHandler requestHandler);
}
