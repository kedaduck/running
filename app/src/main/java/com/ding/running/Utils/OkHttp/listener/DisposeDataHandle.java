package com.ding.running.Utils.OkHttp.listener;

/**
 * @ClassName DisposeDataHandle
 * @Author Leoren
 * @Date 2019/4/15 12:40
 * Description :  处理响应
 * @Version v1.0
 */
public class DisposeDataHandle {

    public DisposeDataListener listener = null;

    public Class<?> clazz = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.listener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }
}
