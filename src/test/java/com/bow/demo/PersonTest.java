package com.bow.demo;

import org.junit.Test;

/**
 * @author vv
 * @since 2016/10/3.
 */
public class PersonTest {
    @Test
    public void test(){
        Person p = null;
        try{
            p = new Person(-1);
        }catch (Exception e){
            e.printStackTrace();
        }
        p.setName("sandy");
        System.out.println(p.getName());
    }
}
