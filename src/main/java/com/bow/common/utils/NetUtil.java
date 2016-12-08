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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static URL toURL(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        String[] split = address.split(":");
        return new URL(split[0], Integer.parseInt(split[1]));
    }

    /**
     * 将socketAddress转换为127.0.0.1:8080这样的字符串
     * @param address
     * @return String
     */
    public static String toString(SocketAddress address) {
        if (address == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
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

    /**
     * parse
     * @param urlString urlString
     * @return URL
     */
    public static URL parse(String urlString){
        Pattern pattern = Pattern.compile("(.*)://([0-9\\.]+):(\\d+)/(.*)");
        Matcher matcher = pattern.matcher(urlString);
        if(!matcher.matches()){
            throw new ShineException(ShineExceptionCode.fail,"wrong url format "+urlString);
        }

        URL url = new URL(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)));
        String remain = matcher.group(4);
        int separator = remain.indexOf("?");
        if(separator<0){
            //没有其他参数
            url.setResource(remain);
        }else{
            String resource = remain.substring(0,separator);
            url.setResource(resource);
            String paramStr = remain.substring(separator+1);
            String[] paramAry = paramStr.split("&");
            for(String param : paramAry){
                String[] entry = param.split("=");
                if(entry.length!=2){
                    throw new ShineException(ShineExceptionCode.fail,"wrong url format "+urlString);
                }
                url.setParameter(entry[0], entry[1]);
            }
        }
        return url;
    }

}
