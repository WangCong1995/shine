package com.bow.common.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * RequestId Generator
 * @author vv
 * @since 2016/12/4.
 */
public class RequestIdGenerator {

    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    public static long getRequestId() {
        // after grow to double maximum , +1 make it be minimum
        return SEQUENCE.incrementAndGet();
    }
}
