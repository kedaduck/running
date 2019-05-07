package com.ding.running.MyViews;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @ClassName ProgressDialogUtil
 * @Author Leoren
 * @Date 2019/4/30 17:09
 * Description :
 * @Version v1.0
 */
public class ProgressDialogUtil {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    public static void showProgressDialog1(Context context, String msg){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    public static void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
