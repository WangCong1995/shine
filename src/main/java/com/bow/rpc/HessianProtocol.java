package com.bow.rpc;

import com.bow.config.Name;
import com.bow.config.ShineConfig;
import com.bow.remoting.hessian.HessianClient;
import com.bow.remoting.hessian.HessianServer;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineServer;

/**
 * hessian protocol
 * 
 * @author vv
 * @since 2016/8/20.
 */
@Name("hessian")
public class HessianProtocol extends AbstractProtocol {

    @Override
    public String getName() {
        Name name = this.getClass().getAnnotation(Name.class);
        return name.value();
    }

    @Override
    protected ShineServer doInitializeServer() {
        return new HessianServer(ShineConfig.getServicePort());
    }

    @Override
    protected ShineClient doInitializeClient(URL serverLocation) {
        return new HessianClient(serverLocation);
    }

}
