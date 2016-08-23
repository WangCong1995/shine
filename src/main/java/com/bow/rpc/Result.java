package com.bow.rpc;

/**
 * Created by vv on 2016/8/20.
 */
public class Result {
    /**
     * success
     */
    private Object value;
    /**
     * exception
     */
    private Throwable cause;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
