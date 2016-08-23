package com.bow.config.spring;

import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.ServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by vv on 2016/8/19.
 */
public class ShineBeanDefinitionParser implements BeanDefinitionParser {

    private static final Logger logger = LoggerFactory.getLogger(ShineBeanDefinitionParser.class);

    private Class<?> beanClass;

    public ShineBeanDefinitionParser(Class<?> beanClass){
        this.beanClass = beanClass;
    }
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);

        String id = element.getAttribute("id");
        beanDefinition.getPropertyValues().addPropertyValue("id", id);

        String interfaceName = element.getAttribute("interface");
        beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
        try {
            beanDefinition.getPropertyValues().addPropertyValue("interfaceClass", Class.forName(interfaceName));
        } catch (ClassNotFoundException e) {
            logger.error(ShineExceptionCode.fail.toString(),e);
        }


        if(ServiceBean.class.equals(beanClass)){
            String refId = element.getAttribute("ref");
            RuntimeBeanReference ref = new RuntimeBeanReference(refId);
            beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
        }

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.setLazyInit(false);
        return beanDefinition;
    }
}
