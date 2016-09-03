package com.bow.common.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vv on 2016/9/1.
 */
public class ShineExecutors {
    /**
     * 对于提交新任务，必须等到有线程池中的线程接收，才解除阻塞
     * @param maxPoolSize 最大线程数
     * @param poolName 线程池名字
     * @return 线程池
     */
    public static ExecutorService newCachedThreadPool(int maxPoolSize, String poolName) {
        return new ThreadPoolExecutor(0, maxPoolSize, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                new NamedThreadFactory(poolName));
    }
}
