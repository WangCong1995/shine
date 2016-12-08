package com.bow.rpc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * URL
 *
 * @author vv
 * @since 2016/8/21.
 */
public class URL implements Serializable{

    public static final String DEFAULT_PROTOCOL = "shine";
    public static final String GROUP = "group";
    public static final String VERSION = "version";

    public URL() {

    }

    public URL(String host, int port) {
        this(DEFAULT_PROTOCOL, host, port, null);
    }

    public URL(String host, int port, String resource) {
        this(DEFAULT_PROTOCOL, host, port, resource);
    }

    public URL(String protocol, String host, int port) {
        this(protocol, host, port, null);
    }

    public URL(String protocol, String host, int port, String resource) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.resource = resource;
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
    private Map<String, Object> parameters = new HashMap<String,Object>();

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

    public void setParameter(String key,Object value){
        this.parameters.put(key,value);
    }

    public Object getParameter(String key){
        return this.parameters.get(key);
    }

    public String getStringParam(String key){
        return this.parameters.get(key).toString();
    }

    public int getIntParam(String key){
        return Integer.parseInt(getStringParam(key));
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
                sb.append(parameter.getKey()).append("=").append(parameter.getValue());
                index++;
            }
        }
        return sb.toString();
    }

    public String getAddress() {
        return host + ":" + port;
    }
}
