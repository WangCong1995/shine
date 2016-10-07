package com.bow.rpc;

import java.io.Serializable;

/**
 * Created by vv on 2016/8/20.
 */
public class Response implements Serializable {

    private long id;
    /**
     * success
     */
    private Object value;
    /**
     * exception
     */
    private Throwable cause;

    public Response(){}

    public Response(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
