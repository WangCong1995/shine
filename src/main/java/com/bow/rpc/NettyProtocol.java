package com.bow.rpc;

import com.bow.config.Name;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineServer;
import com.bow.remoting.netty.NettyClient;
import com.bow.remoting.netty.NettyServer;

/**
 * @author vv
 * @since 2016/9/6.
 */
@Name("netty")
public class NettyProtocol extends AbstractProtocol {

    @Override
    public String getName() {
        Name name = this.getClass().getAnnotation(Name.class);
        return name.value();
    }

    @Override
    protected ShineServer doInitializeServer() {
        return new NettyServer();
    }

    @Override
    protected ShineClient doInitializeClient(URL serverLocation) {
        return new NettyClient(serverLocation);
    }
}
