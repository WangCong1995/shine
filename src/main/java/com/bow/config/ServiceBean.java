package com.bow.config;

import com.bow.common.ExtensionLoader;
import com.bow.common.utils.SpringContext;
import com.bow.rpc.Protocol;
import com.bow.rpc.ProtocolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author vv
 * @since 2016/8/19.
 */
public class ServiceBean<T> extends ServiceConfig implements InitializingBean, DisposableBean, ApplicationListener,ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ServiceBean.class);
    @Override
    public void destroy() throws Exception {
        //注销时取消注册
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
            if (logger.isDebugEnabled()) {
                logger.debug("begin to export service: " + getInterfaceName());
            }
            //export
            Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(ShineConfig.getProtocol());
            protocol.export(this);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //做配置校验

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.addApplicationContext(applicationContext);
    }
}
