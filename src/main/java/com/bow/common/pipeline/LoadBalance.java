package com.bow.common.pipeline;

import com.bow.rpc.URL;

import java.util.List;

/**
 * @author vv
 * @since 2016/10/7.
 */
public interface LoadBalance {
    String RANDOM = "random";
    String ROUND_ROBIN = "roundRobin";

    URL select(String serviceName, List<URL> urls);

}
