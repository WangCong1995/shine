package com.bow.demo.integration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;


/**
 * @author vv
 * @since 2016/8/19.
 */
public class ServerTest {

    @Test
    public void export() throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}
