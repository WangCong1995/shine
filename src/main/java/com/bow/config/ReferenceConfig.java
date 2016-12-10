package com.bow.config;

import com.bow.rpc.URL;

/**
 *
 * @author vv
 * @since 2016/8/19.
 */
public class ReferenceConfig<S> {

    private String id;

    private String interfaceName;

    private Class<S> interfaceClass;

    private String group = "default";

    private String version="";

    private Object mockServiceRef;

    /**
     * 直连地址
     */
    private URL directUrl;

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

    public Class<S> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<S> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public URL getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(URL directUrl) {
        this.directUrl = directUrl;
    }
}
