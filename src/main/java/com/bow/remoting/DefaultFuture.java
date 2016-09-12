package com.bow.remoting;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vv
 * @since 2016/9/6.
 */
public class DefaultFuture<V> implements ShineFuture<V>{

    private final Lock lock = new ReentrantLock();

    private final Condition done = lock.newCondition();

    private List<ShineFutureListener> futureListeners = new ArrayList<>();

    private V result;

    @Override
    public void receive(V result) {
        lock.lock();
        try {
            this.result = result;
            done.signal();
        } finally {
            lock.unlock();
        }
        // 通知监听函数
        if (futureListeners != null) {
            for (ShineFutureListener listener : futureListeners) {
                listener.onSignalComplete();
            }
        }
    }

    @Override
    public V get() {
        return this.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @Override
    public V get(long timeout, TimeUnit unit)  {
        if (timeout < 0) {
            timeout = 0;
        }
        if (!isDone()) {
            long start = System.currentTimeMillis();
            lock.lock();
            try {
                // 被唤醒后还会再次检查isDone
                while (!isDone()) {
                    done.await(timeout, unit);
                    if (isDone() || System.currentTimeMillis() - start > timeout) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new ShineException(ShineExceptionCode.fail, e);
            } finally {
                lock.unlock();
            }
            if (!isDone()) {
                throw new ShineException(ShineExceptionCode.timeoutException);
            }
        }
        return result;
    }


    @Override
    public boolean isDone() {
        return result != null;
    }


    @Override
    public void addFutureListener(ShineFutureListener futureListener){
        this.futureListeners.add(futureListener);
    }
}
