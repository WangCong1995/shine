package com.bow.remoting;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author vv
 * @since 2016/9/8.
 */
public interface ShineFuture<V> {
    V get();

    V get(long timeout, TimeUnit unit);

    boolean isDone();

    void receive(V result);

    void addFutureListener(ShineFutureListener futureListener);
}
