package com.ding.running.MyViews;

import android.widget.Toast;

import com.ding.running.Application.MyApplication;

/**
 * @ClassName ToastUtil
 * @Author Leoren
 * @Date 2019/4/16 16:13
 * Description :
 * @Version v1.0
 */
public class ToastUtil {

    public static void MakeToast(String content){
        Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_LONG).show();
    }
}
