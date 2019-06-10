package com.ding.running.Activity.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ding.running.Activity.ChangePasswordActivity;
import com.ding.running.Activity.LoginActivity;
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
import com.ding.running.Utils.RegexParse;

import org.apache.commons.lang3.StringUtils;
import org.litepal.LitePal;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @ClassName MySelfFragment
 * @Author Leoren
 * @Date 2019/5/6 7:08
 * Description :
 * @Version v1.0
 */
public class MySelfFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private RelativeLayout usernameLayout;
    private RelativeLayout passwordLayout;

    private TextView usernameText;
    private TextView changePasswordText;

    private Button userLogoutBtn;

    private User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_self, container, false);

        user = LitePal.findFirst(User.class);
        initViews();

        return rootView;
    }

    private void initViews(){
        usernameLayout = rootView.findViewById(R.id.user_fix_img_name_view);
        passwordLayout = rootView.findViewById(R.id.user_fix_password_view);

        usernameText = rootView.findViewById(R.id.user_name_text);
        changePasswordText = rootView.findViewById(R.id.change_password_text);

        usernameText.setText(user.getUsername());

        userLogoutBtn = rootView.findViewById(R.id.user_logout_btn);

        usernameLayout.setOnClickListener(this);
        passwordLayout.setOnClickListener(this);
        usernameText.setOnClickListener(this);
        userLogoutBtn.setOnClickListener(this);
        changePasswordText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_fix_img_name_view:
            case R.id.user_name_text:
                changeUsername();
                break;
            case R.id.user_fix_password_view:
            case R.id.change_password_text:
                changePassword();
                break;
            case R.id.user_logout_btn:
                logout();
                break;
            default:
                break;
        }
    }

    private void changeUsername(){
        final EditText editText = new EditText(getContext());
        final AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
        inputDialog.setTitle("更改用户名").setView(editText);
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String username = editText.getText().toString().trim();
                if(StringUtils.isBlank(username)){
                    ToastUtil.MakeToast("用户名不能为空");
                }
                String url = UrlContent.USER_CHANGE_USERNAME;
                RequestBody body = new FormBody.Builder()
                        .add("userId", String.valueOf(user.getTagId()))
                        .add("username", username)
                        .build();
                Request request = CommonRequest.createPostRequest(url, body);
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
                            usernameText.setText(username);
                            user.setUsername(username);
                            user.save();
                        }
                    }

                    @Override
                    public void onFailure(OkHttpException e) {
                        ToastUtil.MakeToast(e.getMsg().toString());
                    }
                }));
            }
        }).show();
    }

    private void changePassword(){
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        startActivity(intent);

    }

    private void logout(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
