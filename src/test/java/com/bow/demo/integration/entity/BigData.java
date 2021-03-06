package com.bow.demo.integration.entity;

import java.io.Serializable;

/**
 * 测试大对象的序列化传输
 * @author vv
 * @since 2016/11/3.
 */
public class BigData implements Serializable{
    private String f1;
    private String f2;
    private String f3;
    private String f4;
    private String f5;

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public String getF5() {
        return f5;
    }

    public void setF5(String f5) {
        this.f5 = f5;
    }
}
