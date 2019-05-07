package com.ding.running.Activity.scenic.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.running.R;

/**
 * @ClassName GoodHolder
 * @Author Leoren
 * @Date 2019/5/6 16:08
 * Description :
 * @Version v1.0
 */
public class GoodHolder extends RecyclerView.ViewHolder {
    public LinearLayout itemGoodView;
    public ImageView goodImg;
    public TextView goodTitleText;
    public TextView goodDetailText;
    public TextView storeNameText;
    public TextView priceText;

    public GoodHolder(@NonNull View itemView) {
        super(itemView);
        itemGoodView = (LinearLayout) itemView;
        goodImg = itemView.findViewById(R.id.item_good_img);
        goodTitleText = itemView.findViewById(R.id.item_good_title);
        goodDetailText = itemView.findViewById(R.id.item_good_detail);
        storeNameText = itemView.findViewById(R.id.item_good_store_name);
        priceText = itemView.findViewById(R.id.item_good_price_text);
    }
}
