package com.ding.running.Utils;

import android.util.Log;

import com.ding.running.Common.ServerResponse;
import com.ding.running.DB.bean.User;
import com.ding.running.vo.AttractionVo;
import com.ding.running.vo.FindPeopleVo;
import com.ding.running.vo.GoodVo;
import com.ding.running.vo.HotelVo;
import com.ding.running.vo.RestaurantVo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @ClassName GsonUtil
 * @Author Leoren
 * @Date 2019/5/6 9:16
 * Description :
 * @Version v1.0
 */
public class GsonUtil {

    private static final String TAG = "GsonUtil";

    private static Gson gson = new Gson();

    public static String formatBeanToJSON(Object obj){
        return gson.toJson(obj);

    }

    public static <T> ServerResponse<T> formatJsonToResponse(String jsonData){
        ServerResponse<T> response = gson.fromJson(jsonData, new TypeToken<ServerResponse<T>>(){}.getType());
        return  response;
    }

    public static  GoodVo formatJsonToGoodVo(String jsonData){
        GoodVo vo = gson.fromJson(jsonData, new TypeToken<GoodVo>(){}.getType());
        return vo;
    }

    public static  HotelVo formatJsonToHotelVo(String jsonData){
        HotelVo vo = gson.fromJson(jsonData, new TypeToken<HotelVo>(){}.getType());
        return vo;
    }

    public static  RestaurantVo formatJsonToRestaurantVo(String jsonData){
        RestaurantVo vo = gson.fromJson(jsonData, new TypeToken<RestaurantVo>(){}.getType());
        return vo;
    }

    public static ServerResponse<User> formatJsonToResponseUser(String jsonData){
        ServerResponse<User> response = null;
        try {
            JSONObject obj = new JSONObject(jsonData);
            int status = obj.optInt("status");
            String msg = obj.optString("msg");
            String dataJsonStr = obj.optString("data");
            User user = gson.fromJson(dataJsonStr, new TypeToken<User>(){}.getType());
            response = new ServerResponse<>(status, msg, user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static <T> List<T> formatJsonToVoList(String jsonDate) {
        Type type = new TypeToken<List<T>>(){}.getType();
        List<T> list = gson.fromJson(jsonDate, type);
        return list;
    }

    public static ServerResponse<List<FindPeopleVo>> formatJsonToFindPeopleList(String jsonData) {
        ServerResponse<List<FindPeopleVo>> response = null;
        try {
            JSONObject obj = new JSONObject(jsonData);
            int status = obj.optInt("status");
            String msg = obj.optString("msg");
            String dataJsonStr = obj.optString("data");
            Log.e("FIND", dataJsonStr);
            List<FindPeopleVo> seasonTripVoList = gson.fromJson(dataJsonStr, new TypeToken<List<FindPeopleVo>>(){}.getType());
            response = new ServerResponse<List<FindPeopleVo>>(status, msg, seasonTripVoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ServerResponse<List<AttractionVo>> formatJsonToAttractionList(String jsonData) {
        ServerResponse<List<AttractionVo>> response = null;
        try {
            JSONObject obj = new JSONObject(jsonData);
            int status = obj.optInt("status");
            String msg = obj.optString("msg");
            String dataJsonStr = obj.optString("data");
            List<AttractionVo> seasonTripVoList = gson.fromJson(dataJsonStr, new TypeToken<List<AttractionVo>>(){}.getType());
            response = new ServerResponse(status, msg, seasonTripVoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;

    }

    public static ServerResponse<List<GoodVo>> formatJsonToGoodList(String jsonData) {
        ServerResponse<List<GoodVo>> response = null;
        try {
            JSONObject obj = new JSONObject(jsonData);
            int status = obj.optInt("status");
            String msg = obj.optString("msg");
            String dataJsonStr = obj.optString("data");
            List<GoodVo> seasonTripVoList = gson.fromJson(dataJsonStr, new TypeToken<List<GoodVo>>(){}.getType());
            response = new ServerResponse(status, msg, seasonTripVoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ServerResponse<List<HotelVo>> formatJsonToHotelList(String jsonData) {
        ServerResponse<List<HotelVo>> response = null;
        try {
            JSONObject obj = new JSONObject(jsonData);
            int status = obj.optInt("status");
            String msg = obj.optString("msg");
            String dataJsonStr = obj.optString("data");
            List<HotelVo> seasonTripVoList = gson.fromJson(dataJsonStr, new TypeToken<List<HotelVo>>(){}.getType());
            response = new ServerResponse(status, msg, seasonTripVoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;

    }

    public static ServerResponse<List<RestaurantVo>> formatJsonToRestaurantList(String jsonData) {
        ServerResponse<List<RestaurantVo>> response = null;
        try {
            JSONObject obj = new JSONObject(jsonData);
            int status = obj.optInt("status");
            String msg = obj.optString("msg");
            String dataJsonStr = obj.optString("data");
            List<RestaurantVo> seasonTripVoList = gson.fromJson(dataJsonStr, new TypeToken<List<RestaurantVo>>(){}.getType());
            response = new ServerResponse(status, msg, seasonTripVoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
