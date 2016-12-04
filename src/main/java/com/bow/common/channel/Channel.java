package com.bow.common.channel;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

import java.net.InetSocketAddress;

/**
 * 与远端通信的抽象通道<br/>
 * client 发送请求是通过Channel实现的
 * 
 * @author vv
 * @since 2016/12/4.
 */
public interface Channel {
    /**
     * get Local Address
     * @return InetSocketAddress
     */
    InetSocketAddress getLocalAddress();

    /**
     * get Remote Address
     * @return InetSocketAddress
     */
    InetSocketAddress getRemoteAddress();

    /**
     * 发送请求到服务器
     * @param request request
     * @return Response
     */
    Response call(Request request);

}
