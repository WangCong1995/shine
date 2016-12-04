package com.bow.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Response
 *
 * @author vv
 * @since 2016/8/20.
 */
public class Response implements Serializable {

    /**
     * 对应的requestId
     */
    private long id;

    /**
     * success
     */
    private Object value;

    /**
     * exception
     */
    private Throwable cause;

    public Response() {
    }

    public Response(long id) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("responseId: ").append(id).append(" ");
        if (value != null) {
            sb.append(" value=").append(JSON.toJSONString(value));
        }
        if (cause != null) {
            sb.append(" cause=").append(JSON.toJSONString(cause.getStackTrace()));
        }
        return sb.toString();
    }
}
