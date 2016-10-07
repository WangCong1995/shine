package com.bow.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * message transport to server
 * @author vv
 * @since 2016/8/20.
 */
public class Request implements Serializable {

    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    private final long id ;
    private String group = "default";
    private String interfaceName;
    private String version;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    /**
     * 请求的协议，地址
     */
    private URL serverUrl;

    public Request(){
        this.id = newId();
    }

    public Request(long id){
        this.id = id;
    }

    private long newId() {
        //after grow to double maximum , +1 make it be minimum
        return SEQUENCE.getAndIncrement();
    }

    public long getId() {
        return id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public URL getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(group).append("#");
        sb.append(interfaceName).append("#").append(methodName);
        sb.append("(");
        int index = 0;
        if(parameters!=null){

            for(Object param:parameters){
                if(index!=0){
                    sb.append(",");
                }
                index++;
                sb.append(JSON.toJSONString(param));
            }

        }
        sb.append(")");
        return  sb.toString();
    }
}
