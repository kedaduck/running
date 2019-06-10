package com.ding.running.Activity.scenic;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ding.running.R;
import com.ding.running.Utils.BitmapUtil;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class GoodDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView goodImg;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);

        content = getIntent().getStringExtra("content");

        toolbar = findViewById(R.id.good_detail_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodDetailActivity.this.finish();
            }
        });
        goodImg = findViewById(R.id.good_detail_img);
//        Bitmap logo = BitmapUtil.netPicToBmp(null);
        Bitmap bitmap = CodeCreator.createQRCode(content, 250, 250, null);
        goodImg.setImageBitmap(bitmap);
    }
}
