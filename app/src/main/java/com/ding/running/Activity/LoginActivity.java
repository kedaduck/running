package com.ding.running.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {

    private EditText user_tel;//手机号码
    private EditText user_psw;//密码
    private Button btn_login;//登录按钮
    private Button btn_toReg;//注册按钮


    private SharedPreferences login_sp;
    private Button btn_toFindPsw;

    private ProgressDialog progressDialog;

    /*************************/
    private CustomVideoView videoview;

    /*************************/

    /**************IBEACON*********/
    private static final int PERMISSION_GRANTED = 0;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;


    /**************IBEACON*********/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //判断是否有权限
        requestPermission();
        if( !initialize()){
            ToastUtil.MakeToast("请您设置权限后使用该APP");
        }

        initView();
        //初始化数据
        user_tel = findViewById(R.id.user_tel);
        user_psw = findViewById(R.id.user_psw);
        btn_login = findViewById(R.id.btn_login);
        btn_toReg = findViewById(R.id.btn_toReg);
        btn_toFindPsw = findViewById(R.id.btn_toFindPsw);

        login_sp = getSharedPreferences("userInfo",0);
        MyListener myListener = new MyListener();
        btn_toReg.setOnClickListener(myListener);
        btn_login.setOnClickListener(myListener);
        btn_toFindPsw.setOnClickListener(myListener);


    }

    private void requestPermission(){
        List<String> premissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED) {
           premissionList.add(Manifest.permission_group.LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.BLUETOOTH_ADMIN);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.BLUETOOTH);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!premissionList.isEmpty()){
            String[] permission = premissionList.toArray(new String[premissionList.size()]);
            ActivityCompat.requestPermissions(this, permission, 1);
        }
    }

    /***************IBEACON*********************/


    private boolean initialize() {
        // 判断手机等设备是否支持BLE，即是否可以扫描iBeacon设备
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /***************IBEACON**********************/




    /*************************************/
    private void initView() {
        //加载视频资源控件
        videoview = findViewById(R.id.videoview);
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

    /*****************************************/
    private class MyListener implements View.OnClickListener{
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    login();
                    break;
                case R.id.btn_toReg:
                    Intent intent_reg = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent_reg);
                    break;


            }
        }
    }


    //登陆按钮监听事件
    public void login(){
        if (!isTelAndPswValid()){
            return;
        }
        showProgressDialog();
        final String username = user_tel.getText().toString().trim();
        final String password = user_psw.getText().toString().trim();
        final SharedPreferences.Editor editor = login_sp.edit();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        final Request request = CommonRequest.createPostRequest(UrlContent.USER_LOGIN, body);
        CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(final String responseStr) {
                closeProgressDialog();
                ServerResponse<User> response = GsonUtil.formatJsonToResponseUser(responseStr);
                if(response == null){
                    onFailure(new OkHttpException(ResponseCode.NETWORK_ERROR.getCode(), ResponseCode.NETWORK_ERROR.getValue()));
                    return;
                }
                if (response.getStatus() == ResponseCode.SUCCESS.getCode()) {
                    User user = response.getData();
                    //todo  把该user 加入到本地数据库中
                    user.setPassword(password);
                    user.setTagId(user.getId());
                    user.save();
                    User user1 = LitePal.findFirst(User.class);
                    Log.e("Find", user1.toString());
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

    //判断手机号码，密码是否有空
    public boolean isTelAndPswValid(){
        if (user_tel.getText().toString().trim().equals("")){
            Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (user_psw.getText().toString().trim().equals("")){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
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
