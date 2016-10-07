package com.bow.common.utils;

import com.bow.config.ReferenceConfig;
import com.bow.config.ServiceConfig;
import com.bow.rpc.Request;

/**
 * @author vv
 * @since 2016/9/10.
 */
public class ShineUtils {

    public static final String HASH_KEY = "#";

    public static final String AT_SIGN = "@";

    public static final String SLASH = "/";

    /**
     * serviceName 如 vv#com.bow.shine.IHello#1.1.1
     * 
     * @return groupName#interface#version
     */
    public static String getServiceName(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getGroup());
        sb.append(HASH_KEY);
        sb.append(request.getInterfaceName());
        if(request.getVersion()!=null){
            sb.append(HASH_KEY);
            sb.append(request.getVersion());
        }
        return sb.toString();
    }

    public static String getServiceName(ServiceConfig serviceConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceConfig.getGroup());
        sb.append(HASH_KEY);
        sb.append(serviceConfig.getInterfaceName());
        if(serviceConfig.getVersion()!=null){
            sb.append(HASH_KEY);
            sb.append(serviceConfig.getVersion());
        }
        return sb.toString();
    }

    public static String getServiceName(ReferenceConfig referenceConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(referenceConfig.getGroup());
        sb.append(HASH_KEY);
        sb.append(referenceConfig.getInterfaceName());
        if(referenceConfig.getVersion()!=null){
            sb.append(HASH_KEY);
            sb.append(referenceConfig.getVersion());
        }
        return sb.toString();
    }

}
