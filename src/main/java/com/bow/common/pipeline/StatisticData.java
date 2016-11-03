package com.bow.common.pipeline;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author vv
 * @since 2016/10/6.
 */
public class StatisticData {
    private AtomicLong clientRequestNum = new AtomicLong(0);

    private AtomicLong clientSuccessRequestNum = new AtomicLong(0);

    private AtomicLong clientFailRequestNum = new AtomicLong(0);

    private AtomicLong clientDiscardedRequestNum = new AtomicLong(0);

    private long maxDelayTime;

    private long minDelayTime;

    private long averageDelayTime;

    private long account = 0;

    private AtomicLong serverRequestNum = new AtomicLong(0);

    private AtomicLong serverSuccessRequestNum = new AtomicLong(0);

    private AtomicLong serverFailRequestNum = new AtomicLong(0);

    private AtomicLong serverDiscardedRequestNum = new AtomicLong(0);

    public void addClienRequestNum() {
        this.clientRequestNum.getAndIncrement();
    }

    public void addClientSuccessRequestNum() {
        this.clientSuccessRequestNum.getAndIncrement();
    }

    public void addClientFailRequestNum() {
        this.clientFailRequestNum.getAndIncrement();
    }

    public void addClientDiscardedRequestNum() {
        this.clientDiscardedRequestNum.getAndIncrement();
    }

    public void setTimeDelay(long timeDelay) {
        maxTimeDelay(timeDelay);
        minTimeDelay(timeDelay);
        averageTimeDelay(timeDelay);
    }

    private void maxTimeDelay(long timeDelay) {
        this.maxDelayTime = (timeDelay > this.maxDelayTime ? timeDelay : this.maxDelayTime);
    }

    private void minTimeDelay(long timeDelay) {
        if (this.minDelayTime == 0L) {
            this.minDelayTime = timeDelay;
        } else {
            this.minDelayTime = (this.minDelayTime > timeDelay ? timeDelay : this.minDelayTime);
        }
    }

    private void averageTimeDelay(long timeDelay) {
        this.averageDelayTime += (timeDelay - this.averageDelayTime) / ++this.account;
    }

    public void addServerRequestNum() {
        this.serverRequestNum.getAndIncrement();
    }

    public void addServerSuccessRequestNum() {
        this.serverSuccessRequestNum.getAndIncrement();
    }

    public void addServerFailRequestNum() {
        this.serverFailRequestNum.getAndIncrement();
    }

    public void addServerDiscardedRequestNum() {
        this.serverDiscardedRequestNum.getAndIncrement();
    }


    /**
     * 下面的方法都是获取值
     * @return 请求的数量
     */
    public long getClientRequestNum() {
        return this.clientRequestNum.intValue();
    }

    public long getClientSuccessRequestNum() {
        return this.clientSuccessRequestNum.intValue();
    }

    public long getClientFailRequestNum() {
        return this.clientFailRequestNum.intValue();
    }

    public long getClientDiscardedRequestNum() {
        return this.clientDiscardedRequestNum.intValue();
    }

    public long getServerRequestNum() {
        return this.serverRequestNum.intValue();
    }

    public long getServerSuccessRequestNum() {
        return this.serverSuccessRequestNum.intValue();
    }

    public long getServerFailRequestNum() {
        return this.serverFailRequestNum.intValue();
    }

    public long getServerDiscardedRequestNum() {
        return this.serverDiscardedRequestNum.intValue();
    }

    private int arraySum(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
}
