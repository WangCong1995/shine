package com.bow.demo;

/**
 * Created by vv on 2016/8/19.
 */
public class DefaultCalculator implements Calculator {

    @Override
    public int calculate(int a, int b) {
        return (a+b)*2;
    }
}
