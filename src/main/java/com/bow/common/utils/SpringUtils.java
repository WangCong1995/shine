package com.bow.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 *
 * @author ViVi
 * @since 2015年5月30日 下午4:55:55
 */
public final class SpringUtils implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T beanOfType(Class<T> clazz){
        return BeanFactoryUtils.beanOfType(applicationContext,clazz);
    }

    public static <T> Map<String,T> beansOfTypeIncludingAncestors(Class<T> clazz){
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,clazz);
    }

}
