package com.bow.demo;

import com.bow.config.ServiceBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;


/**
 * Created by vv on 2016/8/19.
 */
public class ServerTest {

    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
        ServiceBean<DefaultCalculator> bean = context.getBean("calculatorService",ServiceBean.class);
        System.out.println(bean.getRef());
    }

    @Test
    public void export() throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
