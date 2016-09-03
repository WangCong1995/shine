package com.bow.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by vv on 2016/9/3.
 */
@Component
public class ContextInitailizedListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ContextInitailizedListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent){
            logger.info("spring context initialized");

        }
    }
}
