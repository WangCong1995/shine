package com.bow.rpc;

import java.util.Map;

/**
 * Created by vv on 2016/8/21.
 */
public class URL {
    public URL(){

    }

    public URL(String host,int port){
        this(null,host,port,null);
    }

    public URL(String host,int port,Map<String,Object> parameters){
        this(null,host,port,parameters);
    }

    public URL(String protocol,String host,int port,Map<String,Object> parameters){
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.parameters = parameters;
    }


    private String protocol;
    private String host;
    private int port;
    private Map<String,Object> parameters;

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
}
