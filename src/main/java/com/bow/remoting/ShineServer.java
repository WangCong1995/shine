package com.bow.remoting;

import com.bow.rpc.RequestHandlerAware;

/**
 * receive & reply client request
 * @author vv
 * @since 2016/8/19
 */
public interface ShineServer extends RequestHandlerAware{

    boolean start();

    boolean stop();

}
