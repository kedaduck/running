package com.ding.running.Activity.scenic;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.vo.GoodVo;
import com.yzq.zxinglibrary.encode.CodeCreator;

import org.w3c.dom.Text;

public class GoodShowActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView titleText;
    private ImageView goodImg;
    private TextView detailText;
    private TextView priceText;
    private TextView storeText;
    private TextView totalText;
    private ImageView removeBtn;
    private TextView countText;
    private ImageView addBtn;
    private Button buyBtn;

    String content = "";
    private GoodVo vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_show);

        content = getIntent().getStringExtra("content");
        vo = GsonUtil.formatJsonToGoodVo(content);

        initLayout();
    }

    private void initLayout(){
        toolbar = findViewById(R.id.good_show_toolbar);
        titleText = findViewById(R.id.good_name);
        goodImg = findViewById(R.id.good_img);
        detailText = findViewById(R.id.good_text);
        priceText = findViewById(R.id.good_price_text);
        storeText = findViewById(R.id.good_store_name);
        totalText = findViewById(R.id.total_price_text);
        removeBtn = findViewById(R.id.remove_item_btn);
        countText = findViewById(R.id.good_count_text);
        addBtn = findViewById(R.id.add_item_btn);
        buyBtn = findViewById(R.id.buy_good_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodShowActivity.this.finish();
            }
        });

        removeBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);

        titleText.setText(vo.getName());
        detailText.setText(vo.getText());
        priceText.setText("￥" + vo.getPrice());
        Glide.with(this).load(vo.getPicture()).into(goodImg);
        storeText.setText(vo.getStorename());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.remove_item_btn:
                removeItem();
                break;
            case R.id.add_item_btn:
                addItem();
                break;
            case R.id.buy_good_btn:
                buyGood();
                break;
            default:
                break;
        }
    }

    private void removeItem(){
        int count = Integer.parseInt(countText.getText().toString().trim());
        if(count > 0){
            count  = count - 1;
            countText.setText(count + "");
        }
        totalText.setText("￥" + (vo.getPrice() * count));
    }

    private void addItem(){
        int count = Integer.parseInt(countText.getText().toString().trim());
        count  = count + 1;
        countText.setText(count + "");
        totalText.setText("￥" + (vo.getPrice() * count));
    }

    private void buyGood(){
        int count = Integer.parseInt(countText.getText().toString().trim());
        int total = (int) (vo.getPrice() * count);
        final Dialog dialog = new Dialog(this, R.style.edit_AlertDialog_style);
        dialog.setContentView(R.layout.buy_good_dialog_layout);
        ImageView imageView = dialog.findViewById(R.id.buy_good_img);
        Bitmap bitmap = CodeCreator.createQRCode(total + "", 250, 250, null);
        imageView.setImageBitmap(bitmap);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dialog.onWindowAttributesChanged(lp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



}

