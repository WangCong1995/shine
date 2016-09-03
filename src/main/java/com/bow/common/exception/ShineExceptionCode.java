package com.bow.common.exception;

/**
 * @author vv
 * @since 2016/8/19
 */
public enum ShineExceptionCode {
    success(0,"success"),
    /**
     * default description
     */
    fail(1,"shine exception"),

    /**
     * user config error
     */
    configException(101,"config error"),

    /**
     * can not connect the remote url
     */
    connectionException(102,"connection exception");

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
