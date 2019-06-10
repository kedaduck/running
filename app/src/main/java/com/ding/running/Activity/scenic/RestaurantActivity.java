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
import com.ding.running.vo.RestaurantVo;

public class RestaurantActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView restaurantImg;
    private TextView restaurantName;
    private TextView addressText;
    private TextView typeText;
    private TextView priceText;

    private String content;
    private RestaurantVo vo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        content = getIntent().getStringExtra("content");

        vo = GsonUtil.formatJsonToRestaurantVo(content);

        initLayout();
    }

    private void initLayout(){
        toolbar = findViewById(R.id.restaurant_toolbar);
        restaurantImg = findViewById(R.id.restaurant_img);
        restaurantName = findViewById(R.id.restaurant_name_text);
        addressText = findViewById(R.id.restaurant_address_text);
        typeText = findViewById(R.id.restaurant_type_text);
        priceText = findViewById(R.id.restaurant_price_text);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RestaurantActivity.this.finish();
            }
        });
        Glide.with(this).load(vo.getPicture()).into(restaurantImg);
        restaurantName.setText(vo.getName());
        addressText.setText(vo.getText());
        typeText.setText(vo.getFoodname());
        priceText.setText("￥" + vo.getPrice());

    }
}
