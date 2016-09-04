package com.bow.config;


/**
 * Created by vv on 2016/8/19.
 */
public class ReferenceConfig {

    private String id;
    private String interfaceName;
    private Class<?> interfaceClass;
    private String group;

    private String version;

    private Object mockServiceRef;

    public Object getMockServiceRef() {
        return mockServiceRef;
    }

    public void setMockServiceRef(Object mockServiceRef) {
        this.mockServiceRef = mockServiceRef;
    }

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
