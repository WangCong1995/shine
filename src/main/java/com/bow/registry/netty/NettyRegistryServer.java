package com.bow.registry.netty;

import com.bow.common.utils.NetUtil;
import com.bow.config.ShineConfig;
import com.bow.remoting.netty.NettyServer;

import java.net.InetSocketAddress;

/**
 * @author vv
 * @since 2016/12/11.
 */
public class NettyRegistryServer {

    public void start(){
        InetSocketAddress address = NetUtil.toSocketAddress(ShineConfig.getRegistryUrl());
        NettyServer server = new NettyServer(address.getPort());
        server.start();
    }

    public static void main(String[] args) {
        NettyRegistryServer server = new NettyRegistryServer();
        server.start();
    }
}
