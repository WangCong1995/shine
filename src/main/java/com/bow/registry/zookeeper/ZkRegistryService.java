package com.bow.registry.zookeeper;

import com.bow.config.ServiceConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.URL;

import java.util.List;

/**
 * Created by vv on 2016/8/30.
 */
public class ZkRegistryService implements RegistryService {
    @Override
    public List<URL> lookup(String serviceName) {
        return null;
    }

    @Override
    public boolean register(ServiceConfig serviceConfig, URL providerUrl) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
