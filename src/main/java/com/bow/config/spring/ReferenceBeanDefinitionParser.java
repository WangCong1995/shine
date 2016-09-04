package com.bow.config.spring;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.ReferenceBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author vv
 * @since 2016/9/4.
 */
public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(ReferenceBean.class);

        String id = element.getAttribute("id");
        beanDefinition.getPropertyValues().addPropertyValue("id", id);

        String group = element.getAttribute("group");
        beanDefinition.getPropertyValues().addPropertyValue("group", group);

        String version = element.getAttribute("version");
        beanDefinition.getPropertyValues().addPropertyValue("version", version);

        String interfaceName = element.getAttribute("interface");
        beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
        try {
            beanDefinition.getPropertyValues().addPropertyValue("interfaceClass", Class.forName(interfaceName));
        } catch (ClassNotFoundException e) {
            throw new ShineException(ShineExceptionCode.configException,e);
        }
        String refId = element.getAttribute("mockServiceRef");
        RuntimeBeanReference ref = new RuntimeBeanReference(refId);
        beanDefinition.getPropertyValues().addPropertyValue("mockServiceRef", ref);

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.setLazyInit(false);
        return beanDefinition;
    }
}
