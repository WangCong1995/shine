package com.bow.config;

import com.alibaba.fastjson.JSON;
import com.bow.common.ExtensionLoader;
import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.pipeline.ClientPipeline;
import com.bow.common.pipeline.DefaultClientPipeline;
import com.bow.common.utils.ShineUtils;
import com.bow.registry.RegistryFactory;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.Protocol;
import com.bow.rpc.ProtocolFactory;
import com.bow.rpc.Response;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;

/**
 * 代理interfaceClass对应的远程实现 Created by vv on 2016/8/19.
 */
public class ReferenceBean extends ReferenceConfig
        implements FactoryBean<Object>, MethodInterceptor, InitializingBean, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(ReferenceBean.class);
    /**
     * 客户端调用的代理
     */
    private Object proxy;

    /**
     * 调用方法
     * @param methodInvocation 参数信息
     * @return 结果
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Request request = buildRequest(methodInvocation);
        logger.debug("invoke "+ShineUtils.getServiceName(request));

        ClientPipeline clientPipeline = DefaultClientPipeline.getInstance();
        Response response = clientPipeline.sendRequest(request);
        if (response.getCause() != null) {
            throw new ShineException(ShineExceptionCode.fail, response.getCause());
        }
        return response.getValue();
    }

    private Request buildRequest(MethodInvocation invocation) {
        Request request = new Request();
        request.setGroup(getGroup());
        request.setVersion(getVersion());
        request.setInterfaceName(getInterfaceName());
        Method method = invocation.getMethod();
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(invocation.getArguments());
        if(getDirectUrl()!=null){
            //点对点直连
            request.setServerUrl(getDirectUrl());
        }
        return request;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object getObject() throws Exception {
        // getBean获取的是业务接口的代理
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //订阅服务
        RegistryService registryService = ExtensionLoader.getExtensionLoader(RegistryService.class).getExtension(ShineConfig.getRegistryType());
        String serviceName = ShineUtils.getServiceName(this);
        registryService.subscribe(serviceName);
        logger.debug("subscribed service "+serviceName);
        this.proxy = new ProxyFactory(getInterfaceClass(), this).getProxy(getInterfaceClass().getClassLoader());
    }
}
