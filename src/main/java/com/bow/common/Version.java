package com.bow.common;

/**
 * Created by vv on 2016/8/30.
 */
public class Version implements Comparable<Version> {

    public Version(){}

    public Version(int major){
        this(major,0,0);
    }

    public Version(int major, int minor){
        this(major,minor,0);
    }

    public Version(int major, int minor, int batch) {
        this.major = major;
        this.minor = minor;
        this.batch = batch;
    }

    /**
     * 主版本
     */
    private int major;
    /**
     * 此版本
     */
    private int minor;
    /**
     * 补丁号
     */
    private int batch;

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    @Override
    public int compareTo(Version o) {
        int result = this.major-o.major;
        if(result == 0){
            result = this.minor-o.minor;
        }
        if(result==0){
            result = this.batch-o.batch;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(major).append(".").append(minor).append(".").append(batch);
        return sb.toString();
    }
}
