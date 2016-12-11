package com.bow.registry.netty;

import com.bow.common.utils.NetUtil;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.remoting.netty.NettyClient;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author vv
 * @since 2016/12/11.
 */
public class NettyRegistryProxy implements InvocationHandler,RegistryService {

    private NettyClient client;

    private boolean initialized = false;

    private RegistryService proxy;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        client.call(request);
        return null;
    }

    private RegistryService getProxy() {
        return (RegistryService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{RegistryService.class}, this);
    }

    private void ensureClientInitialized(){
        if(!initialized){
            synchronized (this){
                if(!initialized){
                    URL url = NetUtil.toURL(ShineConfig.getRegistryUrl());
                    client = new NettyClient(url);
                    proxy = getProxy();
                }
            }
        }
    }

    /**
     * 根据服务名查找提供该服务的server的地址
     *
     * @param request 组名,接口全限定名,版本号
     * @return 提供此服务的服务器地址
     */
    @Override
    public List<URL> lookup(Request request) {
        ensureClientInitialized();
        return proxy.lookup(request);
    }

    /**
     * 注册服务
     *
     * @param providerUrl 服务信息节点
     * @return 注册成功/失败
     */
    @Override
    public boolean register(URL providerUrl) {
        ensureClientInitialized();
        return proxy.register(providerUrl);
    }

    /**
     * 订阅
     *
     * @param serviceName 服务名
     */
    @Override
    public void subscribe(String serviceName) {
        ensureClientInitialized();
        proxy.subscribe(serviceName);
    }
}
