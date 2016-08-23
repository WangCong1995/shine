package com.bow.registry;

import com.bow.config.ServiceConfig;

import java.net.URL;
import java.util.List;

/**
 * Created by vv on 2016/8/21.
 */
public interface RegistryService {
    /**
     * 根据服务名查找提供该服务的server的地址
     * @param serviceName
     * @return
     */
    List<URL> lookup(String serviceName);

    /**
     * 注册服务
     * @param serviceConfig 服务信息
     * @param providerUrl 服务提供者地址
     * @return
     */
    boolean register(ServiceConfig serviceConfig,URL providerUrl);

    String getName();
}
