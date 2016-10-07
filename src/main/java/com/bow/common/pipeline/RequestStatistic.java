package com.bow.common.pipeline;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author vv
 * @since 2016/10/7.
 */
public class RequestStatistic {
    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 请求次数
     */
    private AtomicLong requestNum = new AtomicLong(0);

    /**
     * 成功次数
     */
    private AtomicLong successRequestNum = new AtomicLong(0);

    /**
     * 失败次数
     */
    private AtomicLong failRequestNum = new AtomicLong(0);

    /**
     * 延迟
     */
    private long maxDelayTime = 0;

    private long minDelayTime = 0;

    /**
     * 总延迟:只有请求成功才会算延迟
     */
    private long totalDelayTime = 0;

    /**
     * 最后一次调用的时间
     */
    private Date lastRequestTime;

    private RequestStatistic(){

    }

    public static RequestStatistic createInstance(String serviceName){
        RequestStatistic statistic = new RequestStatistic();
        statistic.setServiceName(serviceName);
        statistic.getRequestNum().getAndIncrement();
        statistic.setLastRequestTime(new Date());
        return statistic;
    }

    public void updateOnSendRequest(){
        this.lastRequestTime = new Date();
        this.requestNum.getAndIncrement();
    }

    public void updateOnReceiveResponse(long delayTime){
        this.successRequestNum.getAndIncrement();
        if(delayTime>this.maxDelayTime){
            this.maxDelayTime = delayTime;
        }
        if(this.minDelayTime==0){
            minDelayTime = delayTime;
        }else{
            minDelayTime = minDelayTime<delayTime?minDelayTime:delayTime;
        }

        totalDelayTime += delayTime;
    }

    public void updateOnCatchException(){
        this.failRequestNum.getAndIncrement();
    }

    /**
     * getter and setter
     */
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public AtomicLong getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(AtomicLong requestNum) {
        this.requestNum = requestNum;
    }

    public AtomicLong getSuccessRequestNum() {
        return successRequestNum;
    }

    public void setSuccessRequestNum(AtomicLong successRequestNum) {
        this.successRequestNum = successRequestNum;
    }

    public AtomicLong getFailRequestNum() {
        return failRequestNum;
    }

    public void setFailRequestNum(AtomicLong failRequestNum) {
        this.failRequestNum = failRequestNum;
    }

    public long getMaxDelayTime() {
        return maxDelayTime;
    }

    public void setMaxDelayTime(long maxDelayTime) {
        this.maxDelayTime = maxDelayTime;
    }

    public long getMinDelayTime() {
        return minDelayTime;
    }

    public void setMinDelayTime(long minDelayTime) {
        this.minDelayTime = minDelayTime;
    }

    public Date getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(Date lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}
