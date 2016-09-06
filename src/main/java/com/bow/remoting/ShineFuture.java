package com.bow.remoting;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vv
 * @since 2016/9/6.
 */
public class ShineFuture {

    private final Lock lock = new ReentrantLock();

    private final Condition done = lock.newCondition();

    private List<ShineFutureListener> futureListeners;

    private Object result;

    private static final int DEFAULT_TIMEOUT_MILLIS = 2000;

    public void setSuccess(Object result) {
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
                listener.onComplete(this);
            }
        }
    }

    /**
     * 2s内有结果则返回结果，若没有抛出异常
     * 
     * @return 结果
     */
    public Object get() {
        return this.get(DEFAULT_TIMEOUT_MILLIS);
    }

    public Object get(int timeout) {
        if (timeout <= 0) {
            timeout = DEFAULT_TIMEOUT_MILLIS;
        }
        if (!isDone()) {
            long start = System.currentTimeMillis();
            lock.lock();
            try {
                // 被唤醒后还会再次检查isDone
                while (!isDone()) {
                    done.await(timeout, TimeUnit.MILLISECONDS);
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

    private boolean isDone() {
        return result != null;
    }

    public void setFutureListeners(List<ShineFutureListener> futureListeners) {
        this.futureListeners = futureListeners;
    }
}
