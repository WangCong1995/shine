package com.bow.demo.integration.service;

import com.bow.demo.integration.api.Calculator;

import java.security.InvalidParameterException;

/**
 * Created by vv on 2016/8/19.
 */
public class DefaultCalculator implements Calculator {

    @Override
    public int calculate(int a, int b) {
        return (a+b)*2;
    }

    @Override
    public double divide(int a, int b){
        if(b==0){
            throw new InvalidParameterException("divider must not be 0");
        }
        return (double) a/b;
    }

}
