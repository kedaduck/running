package com.ding.running.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ding.running.Common.ResponseCode;
import com.ding.running.Common.ServerResponse;
import com.ding.running.Common.UrlContent;
import com.ding.running.DB.bean.User;
import com.ding.running.MyViews.CustomVideoView;
import com.ding.running.MyViews.ToastUtil;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.Utils.OkHttp.CommonOkHttpClient;
import com.ding.running.Utils.OkHttp.exception.OkHttpException;
import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.listener.DisposeDataListener;
import com.ding.running.Utils.OkHttp.request.CommonRequest;

import java.util.List;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_reg;
    private EditText user_tel;
    private EditText user_psw;
    private EditText user_psw2;
    private EditText user_name;
    private EditText user_ask;

    private EditText user_height;
    private EditText user_weight;
    private EditText user_long;

    private ProgressDialog progressDialog;

    private CustomVideoView videoview;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_reg = findViewById(R.id.btn_reg);
        user_tel = findViewById(R.id.user_tel);
        user_psw = findViewById(R.id.user_psw);
        user_psw2 = findViewById(R.id.user_psw2);
        user_name = findViewById(R.id.user_name);
        user_ask = findViewById(R.id.user_ask);
        user_height = findViewById(R.id.user_height);
        user_weight = findViewById(R.id.user_weight);
        user_long =  findViewById(R.id.user_long);

        btn_reg.setOnClickListener(MyListener);
        initView();

    }


    private void initView() {
        //加载视频资源控件
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

    }

    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        videoview.stopPlayback();
        super.onStop();
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_reg:
                    reg();
                    break;
            }
        }
    };

    //注册按钮的监听事件
    private void reg() {
        if (!isAllEditTextNull()) {
            return;
        }
        final String userTel = user_tel.getText().toString().trim();
        final String userPsw = user_psw.getText().toString().trim();
        final String userName = user_name.getText().toString().trim();
        final String userHeight = user_height.getText().toString().trim();
        final String userWeight = user_weight.getText().toString().trim();
        final String userAsk = user_ask.getText().toString().trim();
        final String userLong = user_long.getText().toString().trim();
        RequestBody body = new FormBody.Builder()
                .add("username", userName)
                .add("password", userPsw)
                .build();
        final Request request = CommonRequest.createPostRequest(UrlContent.USER_REGISTER, body);
        CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(final String responseStr) {
                closeProgressDialog();
                ServerResponse<User> response = GsonUtil.formatJsonToResponseUser(responseStr);
                if(response == null){
                    onFailure(new OkHttpException(ResponseCode.NETWORK_ERROR.getCode(), ResponseCode.NETWORK_ERROR.getValue()));
                    return;
                }
                if (response.isSuccess()) {
                    startMainPage();
                }
                ToastUtil.MakeToast(response.getMsg());
            }

            @Override
            public void onFailure(OkHttpException e) {
                closeProgressDialog();
                ToastUtil.MakeToast(e.getMsg() + "");

            }
        }));

    }

    private void startMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //判断手机号码，密码，确认密码，昵称,密保问题是否有空
    public boolean isAllEditTextNull() {
        String telNum = "^((13[0-9])|(14([5,7,9]))|(15[0-3,5-9])|(17[0-3,5-9])|(18[0-9]))\\d{8}$";
        boolean isTelNum = Pattern.compile(telNum).matcher(user_tel.getText().toString().trim()).matches();
        if (user_tel.getText().toString().trim().equals("")) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isTelNum) {
            Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_psw.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!user_psw2.getText().toString().trim().equals(user_psw.getText().toString().trim())) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_name.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_height.getText().toString().equals("")) {
            Toast.makeText(this, "身高不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_weight.getText().toString().equals("")) {
            Toast.makeText(this, "体重答案不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_ask.getText().toString().equals("")) {
            Toast.makeText(this, "密保答案不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_long.getText().toString().equals("")) {
            Toast.makeText(this, "步幅不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

