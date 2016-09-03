package com.bow.remoting;

import com.bow.rpc.Message;
import com.bow.rpc.Result;
import com.bow.rpc.URL;


/**
 * 负责接收请求,并从请求中分析出调用的服务名
 * @author vv
 * @since 2016/8/19
 */
public interface ShineClient {
    /**
     * 调用服务端的方法
     * @param url 服务地址信息
     * @param message 请求的内容
     * @return 服务端的响应
     */
    Result call(URL url, Message message);
}
