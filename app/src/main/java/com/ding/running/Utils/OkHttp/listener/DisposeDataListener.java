package com.ding.running.Utils.OkHttp.listener;


import com.ding.running.Utils.OkHttp.exception.OkHttpException;

/**
 * @ClassName DisposeDataListener
 * @Author Leoren
 * @Date 2019/4/15 12:39
 * Description :
 * @Version v1.0
 */
public interface DisposeDataListener {

    public void onSuccess(String responseStr);

    public void onFailure(OkHttpException e);
}
