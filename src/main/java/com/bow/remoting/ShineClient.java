package com.bow.remoting;

import com.bow.rpc.Request;
import com.bow.rpc.Response;


/**
 * 负责接收请求,并从请求中分析出调用的服务名
 * 使用方根据服务端的URL生成client实例
 * @author vv
 * @since 2016/8/19
 */
public interface ShineClient {
    /**
     * 调用服务端的方法
     * @param request 请求的内容
     * @return 服务端的响应
     */
    Response call(Request request);
}
