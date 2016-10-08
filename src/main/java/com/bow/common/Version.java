package com.bow.common;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 * @author vv
 * @since 2016/8/30.
 */
public class Version implements Comparable<Version> {

    private static final String REGEXP_SEPARATOR = "\\.";
    private static final String SEPARATOR = ".";

    private static final Pattern VERSION_PATTERN = Pattern.compile("\\d+(\\.\\d+){0,2}");

    public Version() {
    }

    public Version(String version) {
        if (VERSION_PATTERN.matcher(version).matches()) {
            String[] vs = version.split(REGEXP_SEPARATOR);
            if (vs.length == 3) {
                major = Integer.parseInt(vs[0]);
                minor = Integer.parseInt(vs[1]);
                batch = Integer.parseInt(vs[2]);
            } else if (vs.length == 2) {
                major = Integer.parseInt(vs[0]);
                minor = Integer.parseInt(vs[1]);
            } else {
                major = Integer.parseInt(vs[0]);
            }
        }
    }

    public Version(Integer major) {
        this(major, null, null);
    }

    public Version(Integer major, Integer minor) {
        this(major, minor, null);
    }

    public Version(Integer major, Integer minor, Integer batch) {
        this.major = major;
        this.minor = minor;
        this.batch = batch;
    }

    /**
     * 主版本
     */
    private Integer major;

    /**
     * 此版本
     */
    private Integer minor;

    /**
     * 补丁号
     */
    private Integer batch;

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * 3.2 大于任何3.2.*
     * @param o
     * @return
     */
    @Override
    public int compareTo(Version o) {
        int result = null2max(this.major) - null2max(o.major);
        if (result == 0) {
            result = null2max(this.minor) - null2max(o.minor);
        }
        if (result == 0) {
            result = null2max(this.batch) - null2max(o.batch);
        }
        return result;
    }

    private int null2max(Integer p){
        if(p==null){
            return 10000;
        }
        return p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (major != null) {
            sb.append(major);
        } else {
            return sb.toString();
        }
        if (minor != null) {
            sb.append(SEPARATOR).append(minor);
        } else {
            return sb.toString();
        }

        if (batch != null) {
            sb.append(SEPARATOR).append(batch);
        }

        return sb.toString();
    }

    public static boolean isVersion(String version) {
        if (version != null && (version.matches("\\d+\\.\\d+\\.\\d+") || version.matches("\\d+\\.\\d+")
                || version.matches("\\d+"))) {
            return true;
        }
        return false;
    }

    /**
     * 是否包含指定version 如3.2 包含3.2.1
     * @param version
     * @return boolean
     */
    public boolean imply(Version version){
        return version.toString().startsWith(this.toString());
    }

}
