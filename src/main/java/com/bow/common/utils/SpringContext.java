package com.bow.common.utils;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vv
 * @since 2016/9/4.
 */
public class SpringContext {
    private static final Set<ApplicationContext> contexts = new HashSet<ApplicationContext>();

    public static void addApplicationContext(ApplicationContext context) {
        contexts.add(context);
    }

    public static void removeApplicationContext(ApplicationContext context) {
        contexts.remove(context);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> type, String name) {
        for (ApplicationContext context : contexts) {
            if (context.containsBean(name)) {
                Object bean = context.getBean(name);
                if (type.isInstance(bean)) {
                    return (T) bean;
                }
            }
        }
        return null;
    }

    public static <T> T getBean(Class<T> clazz){
        for (ApplicationContext context : contexts) {
            return BeanFactoryUtils.beanOfType(context,clazz);
        }
        return null;
    }
}
