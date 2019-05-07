package com.ding.running.Activity.scenic.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.running.R;

/**
 * @ClassName RestaurantHolder
 * @Author Leoren
 * @Date 2019/5/6 16:50
 * Description :
 * @Version v1.0
 */
public class RestaurantHolder extends RecyclerView.ViewHolder {

    public LinearLayout itemRestaurantView;
    public ImageView restaurantImg;
    public TextView restaurantNameText;
    public TextView restaurantDetailText;
    public TextView restaurantPriceText;


    public RestaurantHolder(@NonNull View itemView) {
        super(itemView);
        itemRestaurantView = (LinearLayout) itemView;
        restaurantImg = itemView.findViewById(R.id.item_restaurant_img);
        restaurantNameText = itemView.findViewById(R.id.item_restaurant_title);
        restaurantDetailText = itemView.findViewById(R.id.item_restaurant_detail);
        restaurantPriceText = itemView.findViewById(R.id.item_restaurant_price_text);
    }
}
