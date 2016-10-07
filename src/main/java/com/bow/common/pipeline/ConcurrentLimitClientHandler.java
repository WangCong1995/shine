package com.bow.common.pipeline;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.ShineConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

/**
 * 限制客户端请求的并发数
 * 
 * @author vv
 * @since 2016/10/4.
 */
public class ConcurrentLimitClientHandler extends ShineHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(ConcurrentLimitClientHandler.class);

    private Semaphore semaphore = new Semaphore(ShineConfig.getMaxConcurrentRequestLimits());

    @Override
    public void onSendRequest(ShineHandlerContext context, PipelineMessage message) {
        try {
            this.semaphore.acquire();
            // 在方法内部调用下一个handler
            context.nextSendRequest(message);
        } catch (InterruptedException e) {
            logger.warn("ExecutesLimitHandler available Permits " + semaphore.availablePermits());
            throw new ShineException(ShineExceptionCode.clientRequestRefused, e);
        } finally {
            this.semaphore.release();
        }
    }

}
