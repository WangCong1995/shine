package com.bow.rpc;

/**
 * reply client request
 * @author vv
 * @since 2016/8/21.
 */
public interface RequestHandler {
    Result handle(Message message);
}
