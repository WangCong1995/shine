package com.bow.config;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2016/8/21.
 */
public class ShineConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShineConfig.class);

    private static ShineConfig config = new ShineConfig();
    private String protocol;
    private int servicePort;
    private String registryUrl;


    /**
     * zookeeper , hessian , redis and so on
     */
    private String registryType;

    private int maxConcurrentRequestLimits;

    private String loadBalance;

    private ShineConfig(){
        try{
            protocol = PropertiesUtil.getProperty("protocol");
            servicePort = Integer.parseInt(PropertiesUtil.getProperty("service.port","9000"));
            maxConcurrentRequestLimits = Integer.parseInt(PropertiesUtil.getProperty("max.concurrent.request.limits","100"));
            registryUrl = PropertiesUtil.getProperty("registry.url");
            registryType = PropertiesUtil.getProperty("registry.type");
            loadBalance = PropertiesUtil.getProperty("loadBalance");

        }catch (Exception e){
            logger.error(ShineExceptionCode.configException.toString(),e);
            throw new ShineException(ShineExceptionCode.configException,e);
        }

    }

    public static String getProtocol() {
        return config.protocol;
    }

    public static int getServicePort() {
        return config.servicePort;
    }

    public static String getRegistryUrl() {
        return config.registryUrl;
    }

    public static String getRegistryType() {
        return  config.registryType;
    }

    public static int getMaxConcurrentRequestLimits() {
        return config.maxConcurrentRequestLimits;
    }

    public static String getLoadBalance() {
        return  config.loadBalance;
    }
}
