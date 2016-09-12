package com.bow.common.utils;


import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.rpc.URL;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by vv on 2016/8/21.
 */
public class NetUtil {
    private final Logger logger = LoggerFactory.getLogger(NetUtil.class);

    public static String getLocalHostAddress(){
        String address;
        try {
            address = InetAddress.getLocalHost().getHostAddress();
            return address;
        } catch (UnknownHostException e) {
            throw new ShineException(ShineExceptionCode.fail,e);
        }
    }
    /**
     * 将127.0.0.1:8080转换为InetSocketAddress
     * @param address
     * @return
     */
    public static InetSocketAddress toSocketAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

    /**
     * 将socketAddress转换为127.0.0.1:8080这样的字符串
     * @param addr
     * @return
     */
    public static String socketAddress2String(SocketAddress addr) {
        if (addr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) addr;
        sb.append(inetSocketAddress.getAddress().getHostAddress());
        sb.append(":");
        sb.append(inetSocketAddress.getPort());
        return sb.toString();
    }

    public static String getIpAddress(String address) {
        InetSocketAddress socketAddress = toSocketAddress(address);

        if (null == socketAddress) {
            return "";
        }
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(socketAddress.getAddress().getHostAddress()).append(":").append(socketAddress.getPort());
        return sbBuilder.toString();
    }

}
