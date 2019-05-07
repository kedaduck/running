package com.ding.running.Activity.FindPeople;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import com.ding.running.Common.ServerResponse;
import com.ding.running.Common.UrlContent;
import com.ding.running.MyViews.ProgressDialogUtil;
import com.ding.running.MyViews.ToastUtil;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.Utils.OkHttp.CommonOkHttpClient;
import com.ding.running.Utils.OkHttp.exception.OkHttpException;
import com.ding.running.Utils.OkHttp.listener.DisposeDataHandle;
import com.ding.running.Utils.OkHttp.listener.DisposeDataListener;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FindPeopleDetail extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private RadioGroup selectClassGroup;
    private RadioButton childrenBtn;
    private RadioButton parentBtn;

    private EditText titleEdit;

    private EditText detailEdit;

    private Button selectPhotoBtn;
    private ImageView photoImg;
    private Button publishBtn;

    private int findClass = 1;    // 1  小孩    2 大人

    private String photoFilePath = "";

    private int IMAGE_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people_detail);

        requestPermission();

        initLayout();
    }

    private void requestPermission(){
        List<String> premissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            premissionList.add(Manifest.permission.CAMERA);
        }
        if(!premissionList.isEmpty()){
            String[] permission = premissionList.toArray(new String[premissionList.size()]);
            ActivityCompat.requestPermissions(this, permission, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            ToastUtil.MakeToast("必须由此权限才能使用本程序");
                            finish();
                            return;
                        }
                    }
                }else {
                    ToastUtil.MakeToast("发生未知错误");
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void initLayout(){
        selectClassGroup = findViewById(R.id.select_class_group);
        selectClassGroup.setOnCheckedChangeListener(new MyCheckedChangeListener());
        childrenBtn = findViewById(R.id.radio_find_children);
        childrenBtn.setChecked(true);
        parentBtn = findViewById(R.id.radio_find_parent);

        titleEdit = findViewById(R.id.publish_find_people_edit);
        detailEdit = findViewById(R.id.publish_find_people_detail_edit);

        selectPhotoBtn = findViewById(R.id.publish_find_people_select_photo_btn);
        selectPhotoBtn.setOnClickListener(this);
        photoImg = findViewById(R.id.find_people_img);
        publishBtn = findViewById(R.id.publish_find_people_btn);
        publishBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_find_people_select_photo_btn:
                selectPhoto();
                break;
            case R.id.publish_find_people_btn:
                publishFindPeopleInfo();
                break;
            default:
                break;
        }

    }

    /**
     * 选择照片
     */
    private void selectPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 11:
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        photoFilePath = cursor.getString(columnIndex);  //获取照片路径
                        Log.e("Find",photoFilePath + "=================");
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath);
                        photoImg.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    /**
     * 发布寻人启事
     */
    private void publishFindPeopleInfo(){
        String titleStr = titleEdit.getText().toString().trim();
        String detailStr = detailEdit.getText().toString().trim();
        if(!regexContent(titleStr, detailStr)){
            return;
        }
        ProgressDialogUtil.showProgressDialog1(this, "正在上传");
        File file = new File(photoFilePath);
        String url = UrlContent.CREATE_FINDING_PUBLISH;
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("upload_file", file.getName(), fileBody)
                .addFormDataPart("userId", String.valueOf(10000L))
                .addFormDataPart("findClass", String.valueOf(findClass))
                .addFormDataPart("title", titleStr)
                .addFormDataPart("detail", detailStr)
                .build();
        CommonOkHttpClient.uploadFile1(file, requestBody, url, new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(String responseStr) {
                ProgressDialogUtil.closeProgressDialog();
                ServerResponse response = GsonUtil.formatJsonToResponse(responseStr);
                if(response.isSuccess()){
                    ToastUtil.MakeToast("");
                    FindPeopleDetail.this.finish();
                }

            }

            @Override
            public void onFailure(OkHttpException e) {
                ProgressDialogUtil.closeProgressDialog();
                ToastUtil.MakeToast(e.getMsg() + "");

            }
        }));




    }

    private boolean regexContent(String titleStr, String detailStr){
        if(StringUtils.isBlank(titleStr)){
            ToastUtil.MakeToast("标题不能为空");
            return false;
        }else if(StringUtils.isBlank(detailStr)){
            ToastUtil.MakeToast("内容不能为空");
            return false;
        }
        return true;
    }

    public class MyCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == childrenBtn.getId()){
                findClass = 1;
            }else if(checkedId == parentBtn.getId()){
                findClass = 2;
            }
        }
    }
}
