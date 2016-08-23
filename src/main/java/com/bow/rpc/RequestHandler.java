package com.bow.rpc;

/**
 * Created by vv on 2016/8/21.
 */
public interface RequestHandler {
    Result handle(Message message);
}
