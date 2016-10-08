package com.bow.demo;

import com.bow.common.Version;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * Created by vv on 2016/8/19.
 */
public class ServerTest {

    @Test
    public void export() throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void test(){
        Version v1 = new Version("3.2");
        System.out.println(v1);
        Version v2 = new Version("3.2.1");
        System.out.println(v2);
        System.out.println(v1.imply(v2));
    }
}
