package com.bow.registry;

import com.bow.config.SPI;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

import java.util.List;

/**
 * consumer provider 通过RegistryService操作远端的注册中心
 * @author vv
 * @since 2016/8/21.
 */
@SPI("zookeeper")
public interface RegistryService{
    /**
     * 根据服务名查找提供该服务的server的地址
     * @param request 组名,接口全限定名,版本号
     * @return 提供此服务的服务器地址
     */
    List<URL> lookup(Request request);

    /**
     * 注册服务
     * @param providerUrl 服务信息节点
     * @return 注册成功/失败
     */
    boolean register(URL providerUrl);

    /**
     * 订阅
     * @param serviceName 服务名
     */
    void subscribe(String serviceName);

}
