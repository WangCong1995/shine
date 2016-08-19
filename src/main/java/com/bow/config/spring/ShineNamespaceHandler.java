package com.bow.config.spring;

import com.bow.config.ReferenceBean;
import com.bow.config.ServiceBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by vv on 2016/8/19.
 */
public class ShineNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("service", new ShineBeanDefinitionParser(ServiceBean.class));
        registerBeanDefinitionParser("reference", new ShineBeanDefinitionParser(ReferenceBean.class));
    }
}
