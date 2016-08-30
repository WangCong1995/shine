package com.bow.registry;

import com.bow.config.ServiceConfig;
import com.bow.rpc.URL;


/**
 * Created by vv on 2016/8/21.
 */
public class ServiceInfo {
    private ServiceConfig serviceConfig;
    private URL providerUrl;

    public ServiceInfo(){

    }

    public ServiceInfo(ServiceConfig serviceConfig,URL providerUrl){
        this.serviceConfig = serviceConfig;
        this.providerUrl = providerUrl;
    }

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public URL getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(URL providerUrl) {
        this.providerUrl = providerUrl;
    }
}
