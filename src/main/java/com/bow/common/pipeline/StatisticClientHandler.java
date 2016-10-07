package com.bow.common.pipeline;

import com.bow.common.utils.ShineUtils;
import com.bow.rpc.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author vv
 * @since 2016/10/6.
 */
public class StatisticClientHandler extends ShineHandlerAdapter {

    private static final Integer SYNC_INTERVAL = 60;

    private static Logger logger = LoggerFactory.getLogger(StatisticClientHandler.class);

    private Map<String, RequestStatistic> data0 = new ConcurrentHashMap<String, RequestStatistic>();

    private Map<String, RequestStatistic> data1 = new ConcurrentHashMap<String, RequestStatistic>();

    private Map<Long, Integer> flag = new HashMap<Long, Integer>();

    private int index = 0;

    private Object updateDataLock = new Object();

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public StatisticClientHandler() {
        // 每SYNC_INTERVAL秒同步一次统计数据
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                sync();
            }
        }, 60, SYNC_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void onSendRequest(ShineHandlerContext context, PipelineMessage message) {
        message.getAttributes().put(PipelineMessage.ATTR_BEGIN_TIME_MILLIS, System.currentTimeMillis());
        Request request = message.getRequest();
        // flag记录本次请求的相关数据是记录在data1还是data2中
        flag.put(request.getId(), index);
        Map<String, RequestStatistic> repository;
        if (index == 0) {
            repository = data0;
        } else {
            repository = data1;
        }

        String serviceName = ShineUtils.getServiceName(message.getRequest());
        synchronized (updateDataLock) {
            RequestStatistic requestStatistic = repository.get(serviceName);
            if (requestStatistic == null) {
                requestStatistic = RequestStatistic.createInstance(serviceName);
            } else {
                requestStatistic.updateOnSendRequest();
            }
            repository.put(serviceName, requestStatistic);
        }
        // 调用下一个handler
        context.nextSendRequest(message);
    }

    @Override
    public void onReceiveResponse(ShineHandlerContext context, PipelineMessage message) {
        long beginTime = (long) message.getAttributes().get(PipelineMessage.ATTR_BEGIN_TIME_MILLIS);
        long delayTime = System.currentTimeMillis() - beginTime;
        RequestStatistic requestStatistic = getStatisticData(message);
        if (requestStatistic != null) {
            requestStatistic.updateOnReceiveResponse(delayTime);
        }
    }

    @Override
    public void onCatchException(ShineHandlerContext context, PipelineMessage message) {
        RequestStatistic requestStatistic = getStatisticData(message);
        if (requestStatistic != null) {
            requestStatistic.updateOnCatchException();
        }
    }

    /**
     * 找到这次请求对应的统计数据
     * 
     * @param message
     * @return
     */
    private RequestStatistic getStatisticData(PipelineMessage message) {
        String serviceName = ShineUtils.getServiceName(message.getRequest());
        Request request = message.getRequest();
        RequestStatistic requestStatistic;
        if (flag.get(request.getId()) == 0) {
            requestStatistic = data0.get(serviceName);
        } else {
            requestStatistic = data1.get(serviceName);
        }
        flag.remove(request.getId());
        return requestStatistic;
    }

    /**
     * 将本地这段时间内的统计数据同步到监控中心
     */
    private void sync() {
        // 从data0切换到data1后等待10s 确保data1中的请求都已经有返回了
        switchStatisticRepository();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            logger.warn("exception in waiting for sync", e);
        }
        logger.info(data1.toString());
    }

    private void switchStatisticRepository() {
        if (index == 0) {
            index = 1;
        } else {
            index = 0;
        }
    }
}
