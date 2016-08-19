package com.bow.config;

/**
 * Created by vv on 2016/8/19.
 */
public class RegistryConfig {

    private String address;
    /**
     * shine , zookeeper
     */
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
