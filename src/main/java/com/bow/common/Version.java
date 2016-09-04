package com.bow.common;

import java.util.regex.Pattern;

/**
 * Created by vv on 2016/8/30.
 */
public class Version implements Comparable<Version> {

    private static final String SEPARATOR = ".";
    private static final Pattern VERSION_PATTERN = Pattern.compile("\\d+\\.\\d+\\.\\d+");
    public Version(){}

    public Version(String version){
        if(VERSION_PATTERN.matcher(version).matches()){
            String[] vs = version.split(SEPARATOR);
            if(vs.length==3){
                major = Integer.parseInt(vs[0]);
                minor = Integer.parseInt(vs[1]);
                batch = Integer.parseInt(vs[2]);
            }
        }
    }

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
        sb.append(major).append(SEPARATOR).append(minor).append(SEPARATOR).append(batch);
        return sb.toString();
    }

    public static void main(String[] args) {
        boolean re = VERSION_PATTERN.matcher("1.1..2").matches();
        System.out.println(re);
    }
}
