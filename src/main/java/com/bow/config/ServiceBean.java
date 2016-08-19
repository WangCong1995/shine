package com.bow.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by vv on 2016/8/19.
 */
public class ServiceBean extends ServiceConfig implements InitializingBean, DisposableBean  {
    @Override
    public void destroy() throws Exception {
        //注销时取消注册
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("begin");
    }
}
