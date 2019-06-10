package com.ding.running.Activity.scenic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.vo.HotelVo;

public class HotelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView hotelImg;
    private TextView hotelName;
    private TextView addressText;
    private TextView typeText;
    private TextView priceText;

    private String content;
    private HotelVo vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        content = getIntent().getStringExtra("content");

        vo = GsonUtil.formatJsonToHotelVo(content);

        initLayout();
    }

    private void initLayout(){
        toolbar = findViewById(R.id.hotel_toolbar);
        hotelImg = findViewById(R.id.hotel_img);
        hotelName = findViewById(R.id.hotel_name_text);
        addressText = findViewById(R.id.hotel_address_text);
        typeText = findViewById(R.id.hotel_type_text);
        priceText = findViewById(R.id.hotel_price_text);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelActivity.this.finish();
            }
        });
        Glide.with(this).load(vo.getPicture()).into(hotelImg);
        hotelName.setText(vo.getName());
        addressText.setText(vo.getText());
        typeText.setText(vo.getType());
        priceText.setText("￥" + vo.getPrice() + "/晚");

    }
}
