package com.bow.rpc;

/**
 * 根据请求给出响应,一般request里面会有请求的服务名称和参数值， 服务提供端只需要找到真实的服务然后响应
 * 
 * @author vv
 * @since 2016/8/21.
 */
public interface RequestHandler {
    Response handle(Request request);
}
