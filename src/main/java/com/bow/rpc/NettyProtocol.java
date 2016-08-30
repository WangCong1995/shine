package com.bow.rpc;

import com.bow.config.ServiceConfig;

/**
 * Created by vv on 2016/8/30.
 */
public class NettyProtocol implements Protocol {
    @Override
    public boolean export(ServiceConfig serviceConfig) {
        return false;
    }

    @Override
    public Result refer(Message message) {
        return null;
    }

    @Override
    public String getName() {
        return "netty";
    }
}
