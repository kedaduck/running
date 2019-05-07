package com.ding.running.Application;

import android.content.Context;

import com.ding.running.Utils.OkHttp.CommonOkHttpClient;

import org.litepal.LitePalApplication;

/**
 * @Author Leoren
 * @Date 2019/2/15 10:20
 */
public class MyApplication extends LitePalApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CommonOkHttpClient.initClient();

    }

    public static Context getContext() {
        return context;
    }
}
