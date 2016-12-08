package com.bow.registry;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.rpc.Request;
import com.bow.rpc.URL;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by vv on 2016/8/21.
 */
public class HessianRegistry implements RegistryService {

    private RegistryService proxy;
    private static volatile boolean initialized = false;

    private void init(){
        String registryLocation = "http://"+ShineConfig.getRegistryUrl();
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            proxy =(RegistryService)factory.create(RegistryService.class,registryLocation);
        } catch (MalformedURLException e) {
            throw new ShineException(ShineExceptionCode.fail,e);
        }
    }
    private void ensureInitialized(){
        if(!initialized){
            init();
            initialized = true;
        }
    }
    @Override
    public List<URL> lookup(Request request) {
        ensureInitialized();
        return proxy.lookup(request);
    }

    @Override
    public boolean register(URL providerUrl) {
        ensureInitialized();
        return proxy.register(providerUrl);
    }

    @Override
    public void subscribe(String serviceName) {

    }

    @Override
    public String getName() {
        return "hessian";
    }
}
