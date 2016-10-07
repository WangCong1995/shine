package com.bow.rpc;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by vv on 2016/8/21.
 */
public class URL {
    public URL() {

    }

    public URL(String host, int port) {
        this(null, host, port, null);
    }

    public URL(String host, int port, String resource) {
        this(null, host, port, resource, null);
    }

    public URL(String protocol, String host, int port, String resource) {
        this(protocol, host, port, resource, null);
    }

    public URL(String protocol, String host, int port, String resource, Map<String, Object> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.resource = resource;
        this.parameters = parameters;
    }

    /**
     * 服务是通过什么协议公布的
     */
    private String protocol;

    private String host;

    private int port;

    /**
     * 服务接口的全限定名
     */
    private String resource;

    /**
     * 服务的附加信息,如所在组名，版本号信息，
     */
    private Map<String, Object> parameters;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (protocol != null) {
            sb.append(protocol).append("://");
        }
        sb.append(host).append(":").append(port);
        if (resource != null) {
            sb.append("/").append(resource);
        }

        if(parameters!=null && parameters.size()!=0){
            int index = 0;
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                sb.append(index == 0 ? "?" : "&");
                sb.append(parameter.getKey()).append("=").append(JSON.toJSONString(parameter.getValue()));
                index++;
            }
        }
        return sb.toString();
    }

    public String getAddress() {
        return host + ":" + port;
    }
}
