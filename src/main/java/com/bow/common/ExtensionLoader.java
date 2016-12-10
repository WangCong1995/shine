package com.bow.common;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.Name;
import com.bow.config.SPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Protocol 等接口，可以通过此工具直接获取到其实现类的实例,通过注解@SPI来指定其默认的实现类 如@SPI("类名")<br/>
 * 每个ExtensionLoader对应接口type 和所有该接口的实现的实例instances
 *
 * @author vv
 * @since 2016/10/2.
 */
public class ExtensionLoader<T> {

    private static Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    private static ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap();

    /**
     * 接口名
     */
    private Class<T> type;

    /**
     * 各种实现的实例
     */
    private Map<String, T> instances = new HashMap<String, T>();

    private ExtensionLoader(Class<T> type) {
        this.type = type;
        ServiceLoader serviceLoader = ServiceLoader.load(type);
        Iterator<T> it = serviceLoader.iterator();
        while (it.hasNext()) {
            T service = it.next();
            // service 有名字，就用其名字存储，否则用其类名存储
            if (service.getClass().isAnnotationPresent(Name.class)) {
                Name nameAnnotation = service.getClass().getAnnotation(Name.class);
                String name = nameAnnotation.value();
                instances.put(name, service);
            } else {
                //小写类名的第一个字母
                String name = this.getClass().getSimpleName();
                String first = name.substring(0,1);
                String remain = name.substring(1);
                instances.put(first.toLowerCase()+remain, service);
            }
        }
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        if (loader == null) {
            loader = createExtensionLoader(type);
        }
        return loader;
    }

    private static synchronized <T> ExtensionLoader<T> createExtensionLoader(Class<T> type) {
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        if (loader == null) {
            loader = new ExtensionLoader<T>(type);
            extensionLoaders.putIfAbsent(type, loader);
            loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        }
        return loader;
    }

    public T getExtension() {
        return getExtension(null);
    }

    /**
     * 根据名字获取接口的实现
     * @param name 实例的名字
     * @return 接口实例
     */
    public T getExtension(String name) {
        //如果name为空就使用 SPI 指定的默认实现
        if (StringUtils.isEmpty(name) && type.isAnnotationPresent(SPI.class)) {
            SPI spi = type.getAnnotation(SPI.class);
            name = spi.value();
        }
        if (StringUtils.isEmpty(name)) {
            throw new ShineException(ShineExceptionCode.configException,
                    "must specify a implements for type " + type.getName());
        }
        // 忽略大小写
        for (String key : instances.keySet()) {
            if (name.equalsIgnoreCase(key)) {
                name = key;
            }
        }
        T result = instances.get(name);
        if (result == null) {
            logger.warn("get nothing for type " + type.getName() + " name " + name);
        }
        return result;
    }
}
