package com.bow.demo.integration.service;

import com.bow.demo.integration.api.Calculator;

/**
 * Created by vv on 2016/8/19.
 */
public class DefaultCalculator implements Calculator {

    private String calculatorName;

    @Override
    public int calculate(int a, int b) {
        return (a+b)*2;
    }


    public String getCalculatorName() {
        return calculatorName;
    }

    public void setCalculatorName(String calculatorName) {
        this.calculatorName = calculatorName;
    }
}
