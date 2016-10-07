package com.bow.config.spring;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.ServiceBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * parse element like
 * <p>
 * <shine:service id="calculatorService" interface="com.bow.demo.Calculator" ref
 * ="defaultCalculator" />
 * 
 * @author vv
 * @since 2016/9/4.
 */
public class ServiceBeanDefinitionParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(ServiceBean.class);

        String id = element.getAttribute("id");
        beanDefinition.getPropertyValues().addPropertyValue("id", id);

        String interfaceName = element.getAttribute("interface");
        beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);

        String refId = element.getAttribute("ref");
        String className = element.getAttribute("class");

        try {
            //init service instance
            if(StringUtils.isNotEmpty(refId)){
                RuntimeBeanReference ref = new RuntimeBeanReference(refId);
                beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
            }else if(className != null && className.length() > 0){
                RootBeanDefinition classDefinition = new RootBeanDefinition();
                classDefinition.setBeanClass(Class.forName(className));
                classDefinition.setLazyInit(false);
                //user can config the specified className's property
                parseProperties(element.getChildNodes(), classDefinition);
                beanDefinition.getPropertyValues().addPropertyValue("ref", new BeanDefinitionHolder(classDefinition, id + "Impl"));

            }else{
                throw new ShineException("can't find ref or class in service bean "+id);
            }
            beanDefinition.getPropertyValues().addPropertyValue("interfaceClass", Class.forName(interfaceName));
        } catch (ClassNotFoundException e) {
            throw new ShineException(ShineExceptionCode.configException,e);
        }

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.setLazyInit(false);
        return beanDefinition;
    }


    private static void parseProperties(NodeList nodeList, RootBeanDefinition beanDefinition) {
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    if ("property".equals(node.getNodeName())
                            || "property".equals(node.getLocalName())) {
                        String name = ((Element) node).getAttribute("name");
                        if (name != null && name.length() > 0) {
                            String value = ((Element) node).getAttribute("value");
                            String ref = ((Element) node).getAttribute("ref");
                            if (value != null && value.length() > 0) {
                                beanDefinition.getPropertyValues().addPropertyValue(name, value);
                            } else if (ref != null && ref.length() > 0) {
                                beanDefinition.getPropertyValues().addPropertyValue(name, new RuntimeBeanReference(ref));
                            } else {
                                throw new UnsupportedOperationException("Unsupported <property name=\"" + name + "\"> sub tag, Only supported <property name=\"" + name + "\" ref=\"...\" /> or <property name=\"" + name + "\" value=\"...\" />");
                            }
                        }
                    }
                }
            }
        }
    }
}
