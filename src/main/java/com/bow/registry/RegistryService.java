package com.bow.registry;

import com.bow.config.ServiceConfig;
import com.bow.rpc.URL;

import java.util.List;

/**
 * RegistryService 供服务端或是客户端能调用的api,通过此api注册或是查询到服务信息
 * Created by vv on 2016/8/21.
 */
public interface RegistryService {
    /**
     * 根据服务名查找提供该服务的server的地址
     * @param serviceName 组名#全接口全限定名#版本号  如 vv#com.bow.shine.IHello#0.1
     * @return 提供此服务的服务器地址
     */
    List<URL> lookup(String serviceName);

    /**
     * 注册服务
     * @param serviceConfig 服务信息
     * @param providerUrl 服务提供者地址
     * @return 注册成功/失败
     */
    boolean register(ServiceConfig serviceConfig,URL providerUrl);

    /**
     * 此api实现 的名字，hessian zookeeper http
     * @return 服务的名字
     */
    String getName();
}