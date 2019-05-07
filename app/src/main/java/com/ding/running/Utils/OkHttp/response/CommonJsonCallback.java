package com.ding.running.Utils.OkHttp.response;

import android.os.Handler;
import android.os.Looper;

import com.ding.running.Utils.OkHttp.exception.OkHttpException;
import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.listener.DisposeDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ClassName CommonJsonCallback
 * @Author Leoren
 * @Date 2019/4/15 13:17
 * Description :
 * @Version v1.0
 */
public class CommonJsonCallback implements Callback {

    protected final String RESULT_CODE = "status";
    protected final int RESULT_CODE_SUCCESS_VALUE = 0;
    protected final int RESULT_CODE_ERROR_VALUE = 1;
    protected final String ERRPR_MSG = "msg";
    protected final String EMPTY_MSG = "获取失败";
    protected final String NETWORK_ERROR_MSG = "网络请求错误";


    protected final int NETWORK_ERROR = -1;  //网络请求错误
    protected final int JSON_ERROR = -2;  //json  格式错误
    protected final int OTHER_ERROR = -3;  // 未知错误

    private DisposeDataListener listener;
    private Class<?> clazz;
    private Handler delieverHandler;

    public CommonJsonCallback(DisposeDataHandle handle){
        listener = handle.listener;
        clazz = handle.clazz;
        delieverHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(Call call, final IOException e) {
        delieverHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(new OkHttpException(NETWORK_ERROR, NETWORK_ERROR_MSG));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String result = response.body().string();
        delieverHandler.post(new Runnable() {
            @Override
            public void run() {
                if(!response.isSuccessful() || response.code() != 200 ){
                    listener.onFailure(new OkHttpException(NETWORK_ERROR, NETWORK_ERROR_MSG));
                }
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result){
        if(result == null || result.equals("")){
            listener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        listener.onSuccess(result);
    }
}
