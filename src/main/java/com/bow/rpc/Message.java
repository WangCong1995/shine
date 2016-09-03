package com.bow.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 传递到服务器的信息
 * Created by vv on 2016/8/20.
 */
public class Message implements Serializable {

    private String group = "default";
    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(group).append("#");
        sb.append(interfaceName).append("#").append(methodName);
        sb.append("(");
        if(parameters!=null){
            for(Object param:parameters){
                sb.append(JSON.toJSONString(param));
            }
        }
        sb.append(")");
        return  sb.toString();
    }
}
