package com.bow.demo.integration;

import com.bow.demo.integration.api.Calculator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vv on 2016/8/19.
 */
public class ClientTest {

    @Test
    public void refer(){
        ApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        Calculator calculator = context.getBean("c1",Calculator.class);
        int result = calculator.calculate(1,1);
        System.out.println("result>>> "+result);
    }

}
