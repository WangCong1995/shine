package com.bow.config;

/**
 * Created by vv on 2016/8/19.
 */
public class ServiceConfig<T> {

    private String id;

    /**
     * 接口全限定名
     */
    private String interfaceName;

    private Class<?> interfaceClass;

    /**
     * 实现类对应的实例
     */
    private T ref;

    /**
     * 默认祖名default
     */
    private String group="default";

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getKey(){
        return group+"#"+interfaceName;
    }
}
