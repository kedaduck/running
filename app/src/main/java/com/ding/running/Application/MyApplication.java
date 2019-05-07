package com.ding.running.Application;

import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
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
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }

    public static Context getContext() {
        return context;
    }
}
