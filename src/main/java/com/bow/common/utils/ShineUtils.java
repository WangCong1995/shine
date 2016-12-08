package com.bow.common.utils;

import com.bow.config.ReferenceConfig;
import com.bow.config.ServiceConfig;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

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

}
