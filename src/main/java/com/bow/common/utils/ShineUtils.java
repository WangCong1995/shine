package com.bow.common.utils;

import com.bow.config.ServiceConfig;
import com.bow.rpc.Message;

/**
 * @author vv
 * @since 2016/9/10.
 */
public class ShineUtils {

    public static final String NUM_SIGN="#";
    /**
     * serviceName   如 vv#com.bow.shine.IHello#1.1.1
     * @return groupName#interface#version
     */
    public static String getServiceName(Message message){
        return message.getGroup()+NUM_SIGN+message.getInterfaceName()+NUM_SIGN+ message.getVersion();
    }

    public static String getServiceName(ServiceConfig serviceConfig){
        return serviceConfig.getGroup()+NUM_SIGN+serviceConfig.getInterfaceName()+NUM_SIGN+ serviceConfig.getVersion();
    }



}
