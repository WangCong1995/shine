package com.bow.rpc;

import com.bow.config.Named;
import com.bow.config.SPI;
import com.bow.config.ServiceConfig;

/**
 * define the way to connect service consumer & provider
 * @author vv
 * @since 2016/8/19.
 */
@SPI("netty")
public interface Protocol extends Named {

    /**
     * server export service
     * @param serviceConfig configured in spring xml
     * @return {@code true} represent success to export
     */
    boolean export(ServiceConfig serviceConfig);

    /**
     * client refer service
     * @param request data will be transport to server
     * @return server response
     */
    Response refer(Request request);

}
