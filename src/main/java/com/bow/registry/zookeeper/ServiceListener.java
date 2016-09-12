package com.bow.registry.zookeeper;

/**
 * @author vv
 * @since 2016/9/12.
 */
public interface ServiceListener {
    void serviceChanged(String path);
}
