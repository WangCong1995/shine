package com.bow.demo.module.netty.demo;

import java.util.Date;

/**
 * Created by vv on 2016/9/3.
 */
public class UnixTime {

    private final int value;

    public UnixTime() {
        this((int) (System.currentTimeMillis() / 1000L + 2208988800L));
    }

    public UnixTime(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}