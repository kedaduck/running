package com.ding.running.Utils.OkHttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @ClassName CommonRequest
 * @Author Leoren
 * @Date 2019/4/15 13:01
 * Description :  负责创建各种请求  get  post  download file upload file
 * @Version v1.0
 */
public class CommonRequest {

    /**
     * 创建get  请求方法
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params){
        StringBuffer sb = new StringBuffer(url).append("?");
        if(params != null){
            for(Map.Entry<String, Object> entry : params.urlParams.entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(sb.substring(0, sb.length() - 1)).get().build();
    }

    public static Request createGetRequest(String url, RequestBody requestBody){
        return new Request.Builder().url(url).post(requestBody).build();
    }

    public static Request createGetRequest(String url){
        return new Request.Builder().url(url).build();
    }

    /**
     * 创建post 请求  方法
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params){
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null){
            for(Map.Entry<String, Object> entry : params.urlParams.entrySet()){
                builder.add(entry.getKey(), entry.getValue() + "");
            }
        }
        FormBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }

    public static Request createPostRequest(String url, RequestBody body){
        return new Request.Builder().url(url).post(body).build();
    }

    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest(String url, RequestParams params){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if(params != null){
            for(Map.Entry<String, Object> entry : params.fileParams.entrySet()){
                if(entry.getValue() instanceof File){
                    builder.addPart(MultipartBody.Part.createFormData(entry.getKey(), null, RequestBody.create(FILE_TYPE, (File)entry.getValue())));
                }else {
                    builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(url).post(builder.build()).build();
    }
}
