package com.bow.demo;

import com.bow.config.ServiceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by vv on 2016/8/19.
 */
public class ServerTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
        ServiceBean<DefaultCalculator> bean = context.getBean("calculatorService",ServiceBean.class);
        System.out.println(bean.getRef());

    }
}
