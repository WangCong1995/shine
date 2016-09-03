package com.bow.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vv on 2016/8/19.
 */
public class ClientTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        Calculator calculator = context.getBean("c1",Calculator.class);
        int result = calculator.calculate(1,3);
        System.out.println(result);
    }
}