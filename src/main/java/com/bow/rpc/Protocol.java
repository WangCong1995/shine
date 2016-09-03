package com.bow.rpc;

import com.bow.config.ServiceConfig;

/**
 * define the way to connect service consumer & provider
 * @author vv
 * @since 2016/8/19.
 */
public interface Protocol {

    /**
     * server export service
     * @param serviceConfig configured in spring xml
     * @return {@code true} represent success to export
     */
    boolean export(ServiceConfig serviceConfig);

    /**
     * client refer service
     * @param message data will be transport to server
     * @return server response
     */
    Result refer(Message message);

    /**
     * @return protocol name ,such as "hessian", "netty"
     */
    String getName();
}
