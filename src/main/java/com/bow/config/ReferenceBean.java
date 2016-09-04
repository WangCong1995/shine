package com.bow.config;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.SpringContext;
import com.bow.rpc.Message;
import com.bow.rpc.Protocol;
import com.bow.rpc.ProtocolFactory;
import com.bow.rpc.Result;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;

/**
 * 代理interfaceClass对应的远程实现
 * Created by vv on 2016/8/19.
 */
public class ReferenceBean extends ReferenceConfig implements FactoryBean<Object>, MethodInterceptor, InitializingBean,DisposableBean {

    /**
     * 客户端调用的代理
     */
    private Object proxy;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Protocol protocol = SpringContext.getBean(Protocol.class);
        Message message = invocationToMessage(methodInvocation);
        Result result = protocol.refer(message);
        if(result.getCause()!=null){
            throw new ShineException(ShineExceptionCode.fail,result.getCause());
        }
        return result.getValue();
    }

    private Message invocationToMessage(MethodInvocation invocation){
        Message message = new Message();
        message.setGroup(getGroup());
        message.setInterfaceName(getInterfaceName());
        Method method = invocation.getMethod();
        message.setMethodName(method.getName());
        message.setParameterTypes(method.getParameterTypes());
        message.setParameters(method.getTypeParameters());
        return message;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object getObject() throws Exception {
        //getBean获取的是业务接口的代理
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
        this.proxy = new ProxyFactory(getInterfaceClass(), this).getProxy(getInterfaceClass().getClassLoader());
    }
}
