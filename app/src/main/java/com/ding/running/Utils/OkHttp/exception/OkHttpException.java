package com.ding.running.Utils.OkHttp.exception;

/**
 * @ClassName OkHttpException
 * @Author Leoren
 * @Date 2019/4/15 12:34
 * Description :
 * @Version v1.0
 */
public class OkHttpException extends Exception {

    private static final long serialVersionUID = 1L;

    private int status;

    private Object msg;

    public OkHttpException(int status, Object msg){
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
