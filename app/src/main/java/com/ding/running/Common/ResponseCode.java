package com.ding.running.Common;

/**
 * @Author Leoren
 * @Date 2019/3/2 11:01
 *
 * 服务器返回ServerResponse的一些值
 */
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    NETWORK_ERROR(3, "网络连接失败");

    private int code;
    private String value;

    ResponseCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
