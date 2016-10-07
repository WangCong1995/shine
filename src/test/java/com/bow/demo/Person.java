package com.bow.demo;

import com.bow.common.exception.ShineException;

/**
 * @author vv
 * @since 2016/10/3.
 */
public class Person {
    private String name;
    private int age;

    public Person(int age){
        if(age<0){
            throw new ShineException("too young");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
