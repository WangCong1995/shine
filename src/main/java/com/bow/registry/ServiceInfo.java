package com.bow.registry;

import com.bow.config.ServiceConfig;
import com.bow.rpc.URL;


/**
 * Created by vv on 2016/8/21.
 */
public class ServiceInfo {
    private URL providerUrl;

    public ServiceInfo(){

    }

    public ServiceInfo(ServiceConfig serviceConfig,URL providerUrl){
        this.providerUrl = providerUrl;
    }


    public URL getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(URL providerUrl) {
        this.providerUrl = providerUrl;
    }
}
