package com.bow.common.exception;

/**
 * Created by vv on 2016/8/19.
 */
public enum ShineExceptionCode {
    success(0,"success"),
    fail(1,"shine exception"),
    configException(101,"config error");

    private int code;
    private String message;
    ShineExceptionCode(int code,String msg){
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return code+" : "+message;
    }

}
