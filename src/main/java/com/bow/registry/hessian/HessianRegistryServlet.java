package com.bow.registry.hessian;

import com.bow.common.utils.ShineUtils;
import com.bow.config.Name;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.URL;
import com.caucho.hessian.server.HessianServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class HessianRegistryServlet extends HessianServlet implements RegistryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianRegistryServlet.class);

    private Map<String, List<URL>> repository = new ConcurrentHashMap();

    @Override
    public List<URL> lookup(Request request) {
        String serviceName = ShineUtils.getServiceName(request);
        return repository.get(serviceName);
    }

    /**
     * 注册服务
     * @param providerUrl 服务信息节点
     * @return boolean
     */
    @Override
    public boolean register(URL providerUrl) {
        String serviceName = ShineUtils.getServiceName(providerUrl);
        List<URL> urls = repository.get(serviceName);
        if (urls == null) {
            urls = new ArrayList();
            repository.put(serviceName, urls);
        }
        urls.add(providerUrl);
        LOGGER.debug("register\n" + serviceName + " , " + providerUrl);
        return true;
    }

    @Override
    public void subscribe(String serviceName) {
        //
    }
}
