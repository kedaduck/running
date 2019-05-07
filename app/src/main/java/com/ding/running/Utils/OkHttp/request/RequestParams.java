package com.ding.running.Utils.OkHttp.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RequestParams
 * @Author Leoren
 * @Date 2019/4/15 12:42
 * Description :
 * @Version v1.0
 */
public class RequestParams {

    public ConcurrentHashMap<String, Object> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();

    public RequestParams() {
        this((Map<String, String>) null);
    }

    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

//    public RequestParams(final String key, final Object value) {
//        this(new HashMap<String, Object>() {
//            {
//                put(key, value);
//            }
//        });
//    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }



//    public void put(String key, Object object){
//        if (key != null) {
//            fileParams.put(key, object);
//        }
//    }


}
