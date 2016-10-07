package com.bow.rpc;

import com.bow.remoting.HessianClient;
import com.bow.remoting.HessianServer;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineServer;

/**
 * hessian protocol
 * 
 * @author vv
 * @since 2016/8/20.
 */
public class HessianProtocol extends AbstractProtocol {

    @Override
    public String getName() {
        return "hessian";
    }

    @Override
    protected ShineServer doInitializeServer() {
        return new HessianServer();
    }

    @Override
    protected ShineClient doInitializeClient(URL serverLocation) {
        return new HessianClient(serverLocation);
    }

}
