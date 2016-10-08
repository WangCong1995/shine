package com.bow.registry;

import com.bow.common.utils.ShineUtils;
import com.bow.config.ServiceConfig;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 注册中心的核心
 * Created by vv on 2016/8/21.
 */
public class HessianRegistryService implements RegistryService {
    private List<ServiceInfo> repository = new CopyOnWriteArrayList<>();

    @Override
    public List<URL> lookup(Request request) {
        //FIXME 20161008 zk注册时将参数修改为了Request,这里是否需要改动
        String serviceName = ShineUtils.getServiceName(request);
        List<URL> result = new ArrayList<>();
        for(ServiceInfo service:repository){
            boolean matched = service.getServiceConfig().getKey().equals(serviceName);
            if(matched){
                result.add(service.getProviderUrl());
            }
        }
        return result;
    }

    @Override
    public boolean register(ServiceConfig serviceConfig, URL providerUrl) {
        repository.add(new ServiceInfo(serviceConfig,providerUrl));
        return true;
    }

    @Override
    public void subscribe(String serviceName) {

    }

    @Override
    public String getName() {
        return "hessian";
    }
}
