package com.bow.config;

import com.bow.rpc.Protocol;
import com.bow.rpc.ProtocolFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author vv
 * @since 2016/9/4.
 */
public class ProtocolBean extends ProtocolConfig implements FactoryBean,InitializingBean {
    @Override
    public Object getObject() throws Exception {
        return this;
    }

    @Override
    public Class<?> getObjectType() {
        return Protocol.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
