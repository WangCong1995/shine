package com.bow.config;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vv on 2016/8/21.
 */
public class ShineConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShineConfig.class);

    private static ShineConfig config = new ShineConfig();
    private String protocol;
    private int servicePort;
    private String registryUrl;

    private ShineConfig(){
        try{
            protocol = PropertiesUtil.getProperty("protocol");
            servicePort = Integer.parseInt(PropertiesUtil.getProperty("service.port"));
            registryUrl = PropertiesUtil.getProperty("registry.url");
        }catch (Exception e){
            logger.error(ShineExceptionCode.configException.toString(),e);
            throw new ShineException(ShineExceptionCode.configException,e);
        }

    }

    public static String getProtocol() {
        return config.protocol;
    }

    public static int getservicePort() {
        return config.servicePort;
    }

    public static String getRegistryUrl() {
        return config.registryUrl;
    }

    public void setRegistryUrl(String registryUrl) {
        this.registryUrl = registryUrl;
    }
}
