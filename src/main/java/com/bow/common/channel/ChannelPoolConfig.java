package com.bow.common.channel;

import com.bow.common.utils.PropertiesUtil;

/**
 * 连接池配置, 注意这个单例，外界是可以修改其属性的（方便测试）
 * 
 * @author vv
 * @since 2016/12/4.
 */
public class ChannelPoolConfig {

    private static ChannelPoolConfig config = new ChannelPoolConfig();

    private int minIdle;

    private int maxIdle;

    /**
     * 连接池中最多只有maxTotal个对象，都被借走后。下一个线程就会被阻塞再borrowObject()<br/>
     *
     */
    private int maxTotal;

    /**
     * borrow时的最大等待时间，超时出异常
     */
    private long maxWaitMillis;

    /**
     * 空闲超过指定的时间的对象，会被清除掉
     */
    private long minEvictableIdleTimeMillis;

    private long softMinEvictableIdleTimeMillis;

    /**
     * 默认回收周期
     */
    private long timeBetweenEvictionRunsMillis;

    private boolean lifo;

    private ChannelPoolConfig() {
        minIdle = PropertiesUtil.getInt("minIdle", 3);
        maxIdle = PropertiesUtil.getInt("maxIdle", 5);
        maxTotal = PropertiesUtil.getInt("maxTotal", 8);

        // 配置文件中用的秒
        maxWaitMillis = PropertiesUtil.getInt("maxWaitSeconds", 3) * 1000;
        minEvictableIdleTimeMillis = PropertiesUtil.getInt("minEvictableIdleTimeSeconds", 3600) * 1000;
        softMinEvictableIdleTimeMillis = PropertiesUtil.getInt("softMinEvictableIdleTimeSeconds", 3600) * 1000;
        timeBetweenEvictionRunsMillis = PropertiesUtil.getInt("timeBetweenEvictionRunsSeconds", 600) * 1000;

        lifo = PropertiesUtil.getBoolean("lifo", true);
    }

    /**
     * 单列
     * 
     * @return ChannelPoolConfig
     */
    public static ChannelPoolConfig get() {
        return config;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public long getSoftMinEvictableIdleTimeMillis() {
        return softMinEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public boolean isLifo() {
        return lifo;
    }

    public void setLifo(boolean lifo) {
        this.lifo = lifo;
    }
}
