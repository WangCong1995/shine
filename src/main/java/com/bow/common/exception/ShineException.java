package com.bow.common.exception;

/**
 * Created by vv on 2016/8/19.
 */
public class ShineException extends RuntimeException {

    public ShineException(){
        super(ShineExceptionCode.fail.toString());
    }

    public ShineException(String message){
        super(message);
    }

    public ShineException(String message,Throwable e){
        super(message,e);
    }

}
