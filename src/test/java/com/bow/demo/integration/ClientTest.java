package com.bow.demo.integration;

import com.bow.common.utils.ShineUtils;
import com.bow.demo.integration.api.Calculator;
import com.bow.rpc.URL;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * @author vv
 * @since 2016/8/19.
 */
public class ClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);
    private ApplicationContext context;

    @Before
    public void setup(){
        context = new ClassPathXmlApplicationContext("client.xml");
    }

    @Test
    public void refer(){
        Calculator calculator = context.getBean("c1",Calculator.class);
        int result = calculator.calculate(1,1);
        System.out.println("result>>> "+result);
    }

    @Test
    public void refer1(){
        Calculator calculator = context.getBean(Calculator.class);
        int result = calculator.calculate(1,1);
        System.out.println("result>>> "+result);
    }

    @Test
    public void callWithException(){
        Calculator calculator = context.getBean("c1",Calculator.class);
        try{
            double result = calculator.divide(1,0);
            System.out.println("result>>> "+result);
        }catch (InvalidParameterException e){
            LOGGER.error("catch exception", e);
        }

    }

    @Test
    public void dynamic(){
        List<URL> urls = ShineUtils.getServerUrl(Calculator.class,"vv");
        for(URL url: urls){
            try {
                Calculator calculator = ShineUtils.getService(Calculator.class,url);
                int result = calculator.calculate(1,1);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
