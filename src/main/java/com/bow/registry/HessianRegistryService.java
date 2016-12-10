package com.bow.registry;

import com.bow.common.utils.ShineUtils;
import com.bow.config.Name;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心的核心
 * 
 * @author vv
 * @since 2016/8/21.
 */
@Name("hessian")
public class HessianRegistryService implements RegistryService {

    private Map<String, List<URL>> repository = new ConcurrentHashMap();

    @Override
    public List<URL> lookup(Request request) {
        String serviceName = ShineUtils.getServiceName(request);
        return repository.get(serviceName);
    }

    @Override
    public boolean register(URL providerUrl) {
        String serviceName = ShineUtils.getServiceName(providerUrl);
        List<URL> urls = repository.get(serviceName);
        if (urls == null) {
            urls = new ArrayList();
            repository.put(serviceName, urls);
        }
        urls.add(providerUrl);
        return true;
    }

    @Override
    public void subscribe(String serviceName) {

    }
}
