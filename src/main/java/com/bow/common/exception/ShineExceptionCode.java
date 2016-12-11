package com.bow.common.exception;

/**
 * @author vv
 * @since 2016/8/19
 */
public enum ShineExceptionCode {

    /**
     * success
     */
    success(0, "success"),
    /**
     * default description
     */
    fail(-1, "shine exception"),

    /**
     * user config error
     */
    configException(101, "config error"),

    /**
     * can not connect the remote url
     */
    connectionException(102, "connection exception"),
    /**
     *
     * 超时 异常
     */
    timeoutException(103, "time out exception"),

    /**
     * 服务不存在
     */
    noExistsService(104, "service not exists "),
    /**
     * 客户端请求未能正确获取响应（客户端原因）
     */
    clientRequestRefused(105, "client request refused");

    private int code;

    private String message;

    ShineExceptionCode(int code, String msg) {
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
    public String toString() {
        return code + " : " + message;
    }

}
