package com.bow.config.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by vv on 2016/8/19.
 */
public class ShineNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("service", new ServiceBeanDefinitionParser());
        registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser());
    }
}
