package com.ding.running.Utils.OkHttp;

import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.response.CommonJsonCallback;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @ClassName CommonOkHttpClient
 * @Author Leoren
 * @Date 2019/4/15 12:38
 * Description :
 * @Version v1.0
 */
public class CommonOkHttpClient {

    private static final int TIMEOUT = 5;
    private static OkHttpClient client;

    public static void initClient(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        client = okHttpBuilder.build();
    }

    public static void post(Request request, DisposeDataHandle handle){
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
    }

    public static void get(Request request, DisposeDataHandle handle){
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
    }

    public static void postJsonString(String jsonStr, String url, DisposeDataHandle handle){
        RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), jsonStr);
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
    }

    public static void postFile(File file, String url, DisposeDataHandle handle){

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
    }

    public static void uploadFile(File file, String url, DisposeDataHandle handle){
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("upload_file", file.getName(), fileBody)
                .addFormDataPart("userId", String.valueOf(1))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));

    }

    public static void uploadFile1(File file, RequestBody requestBody, String url, DisposeDataHandle handle){
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));

    }
}
