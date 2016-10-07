package com.bow.common.pipeline;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在管道中传输的信息的载体
 * 
 * @author vv
 * @since 2016/10/5.
 */
public class PipelineMessage {

    public static final String ATTR_BEGIN_TIME_MILLIS = "beginTime";

    private Request request;

    private Response response;

    private Throwable throwable;

    private Map<String, Object> attributes = new ConcurrentHashMap<String,Object>();

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
