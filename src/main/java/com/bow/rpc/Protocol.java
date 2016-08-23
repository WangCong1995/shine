package com.bow.rpc;

import com.bow.config.ServiceConfig;

/**
 * Created by vv on 2016/8/19.
 */
public interface Protocol {
    boolean export(ServiceConfig serviceConfig);
    Result refer(Message message);
    String getName();
}
