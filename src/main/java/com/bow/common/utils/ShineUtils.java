package com.bow.common.utils;

import com.bow.common.ExtensionLoader;
import com.bow.config.ReferenceBean;
import com.bow.config.ReferenceConfig;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

import java.util.List;

/**
 * @author vv
 * @since 2016/9/10.
 */
public class ShineUtils {

    public static final String HASH_KEY = "#";

    public static final String AT_SIGN = "@";

    public static final String SLASH = "/";

    /**
     * serviceName 如 vv#com.bow.shine.IHello
     * 
     * @return groupName#interface
     */
    public static String getServiceName(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getGroup());
        sb.append(HASH_KEY);
        sb.append(request.getInterfaceName());
        return sb.toString();
    }

    public static String getServiceName(URL url) {
        StringBuilder sb = new StringBuilder();
        sb.append(url.getStringParam(URL.GROUP));
        sb.append(HASH_KEY);
        sb.append(url.getResource());
        return sb.toString();
    }

    public static String getServiceName(ServiceConfig serviceConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceConfig.getGroup());
        sb.append(HASH_KEY);
        sb.append(serviceConfig.getInterfaceName());
        return sb.toString();
    }

    public static String getServiceName(ReferenceConfig referenceConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(referenceConfig.getGroup());
        sb.append(HASH_KEY);
        sb.append(referenceConfig.getInterfaceName());
        return sb.toString();
    }

    /**
     * get ServerUrl
     * @param interfaceClass interfaceClass
     * @param group group
     * @return List<URL>
     */
    public static List<URL> getServerUrl(Class interfaceClass, String group){
        RegistryService registryService = ExtensionLoader.getExtensionLoader(RegistryService.class).getExtension(ShineConfig.getRegistryType());
        Request request = new Request();
        request.setInterfaceName(interfaceClass.getName());
        if(group != null){
            request.setGroup(group);
        }
        return registryService.lookup(request);
    }

    /**
     * 根据接口和url动态的获取服务
     * @param interfaceClass interfaceClass
     * @param url url
     * @param <S> s
     * @return  service
     * @throws Exception e
     */
    public static <S> S getService(Class<S> interfaceClass, URL url) throws Exception {
        ReferenceBean<S> referenceBean = new ReferenceBean();
        referenceBean.setGroup(url.getStringParam(URL.GROUP));
        referenceBean.setVersion(url.getStringParam(URL.VERSION));
        referenceBean.setInterfaceName(interfaceClass.getName());
        referenceBean.setInterfaceClass(interfaceClass);
        referenceBean.setDirectUrl(url);
        referenceBean.initProxy();
        return referenceBean.getObject();
    }

}
