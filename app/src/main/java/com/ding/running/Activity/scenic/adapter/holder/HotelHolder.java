package com.ding.running.Activity.scenic.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.running.R;

/**
 * @ClassName HotelHolder
 * @Author Leoren
 * @Date 2019/5/6 16:29
 * Description :
 * @Version v1.0
 */
public class HotelHolder extends RecyclerView.ViewHolder {

    public LinearLayout itemHotelView;
    public ImageView hotelImg;
    public TextView hotelNameText;
    public TextView hotelDetailText;
    public TextView hotelTypeText;
    public TextView hotelPriceText;


    public HotelHolder(@NonNull View itemView) {
        super(itemView);
        itemHotelView = (LinearLayout) itemView;
        hotelImg = itemView.findViewById(R.id.item_hotel_img);
        hotelNameText = itemView.findViewById(R.id.item_hotel_title);
        hotelDetailText = itemView.findViewById(R.id.item_hotel_detail);
        hotelTypeText = itemView.findViewById(R.id.item_hotel_type_name);
        hotelPriceText = itemView.findViewById(R.id.item_hotel_price_text);
    }
}
