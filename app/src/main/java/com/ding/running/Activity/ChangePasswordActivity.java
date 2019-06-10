package com.ding.running.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ding.running.Common.ResponseCode;
import com.ding.running.Common.ServerResponse;
import com.ding.running.Common.UrlContent;
import com.ding.running.DB.bean.User;
import com.ding.running.MyViews.ToastUtil;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.Utils.OkHttp.CommonOkHttpClient;
import com.ding.running.Utils.OkHttp.exception.OkHttpException;
import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.listener.DisposeDataListener;
import com.ding.running.Utils.OkHttp.request.CommonRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.litepal.LitePal;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText oldPasswordEdit;
    private EditText newPasswordEdit;
    private EditText newRePasswordEdit;
    private Button finishChangeBtn;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        user = LitePal.findFirst(User.class);

        toolbar = findViewById(R.id.change_password_toolbar);
        oldPasswordEdit = findViewById(R.id.old_password_edit);
        newPasswordEdit = findViewById(R.id.new_password_edit);
        newRePasswordEdit = findViewById(R.id.new_re_password_edit);

        finishChangeBtn = findViewById(R.id.change_password_btn);
        finishChangeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_password_btn:
                changePassword();
                break;
        }
    }

    private void changePassword(){
        String oldPassword = oldPasswordEdit.getText().toString().trim();
        final String newPassword = newPasswordEdit.getText().toString().trim();
        String newRePassword = newRePasswordEdit.getText().toString().trim();

        if(StringUtils.isBlank(oldPassword)){
            ToastUtil.MakeToast("原密码不能为空");
            return;
        }
        if(StringUtils.isBlank(newPassword)){
            ToastUtil.MakeToast("新密码不能为空");
            return;
        }
        if(!oldPassword.equals(user.getPassword())){
            ToastUtil.MakeToast("原密码输入有误");
            return;
        }
        if(newPassword.equals(oldPassword)){
            ToastUtil.MakeToast("新密码不能和旧密码相同");
            return;
        }
        if(!newPassword.equals(newRePassword)){
            ToastUtil.MakeToast("两次密码输入不一致");
            return;
        }
        String url = UrlContent.USER_CHANGE_PASSWORD;
        RequestBody body = new FormBody.Builder()
                .add("userId", String.valueOf(user.getTagId()))
                .add("newPassword", newPassword)
                .build();
        final Request request = CommonRequest.createPostRequest(url, body);
        CommonOkHttpClient.post(request, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(String responseStr) {
                if(StringUtils.isBlank(responseStr)){
                    onFailure(new OkHttpException(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getValue()));
                    return;
                }
                ServerResponse response = GsonUtil.formatJsonToResponse(responseStr);
                if(response == null){
                    onFailure(new OkHttpException(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getValue()));
                    return;
                }
                if(response.isSuccess()){
                    ToastUtil.MakeToast(response.getMsg());
                    user.setPassword(newPassword);
                    user.save();
                }else {
                    ToastUtil.MakeToast(response.getMsg());
                }

            }

            @Override
            public void onFailure(OkHttpException e) {
                ToastUtil.MakeToast(e.getMsg().toString());
            }
        }));

    }
}
