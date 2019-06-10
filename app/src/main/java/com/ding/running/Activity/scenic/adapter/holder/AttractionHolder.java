package com.ding.running.Activity.scenic.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.running.R;

/**
 * @ClassName AttractionHolder
 * @Author Leoren
 * @Date 2019/5/6 12:06
 * Description :
 * @Version v1.0
 */
public class AttractionHolder extends RecyclerView.ViewHolder {

    public LinearLayout itemAttractionView;
    public ImageView attractionImg;
    public TextView attractionNameText;
    public TextView attractionDetailText;


    public AttractionHolder(@NonNull View itemView) {
        super(itemView);
        itemAttractionView = (LinearLayout) itemView;
        attractionDetailText = itemView.findViewById(R.id.item_attraction_text);
        attractionImg = itemView.findViewById(R.id.item_attraction_img);
        attractionNameText = itemView.findViewById(R.id.item_attraction_title);
    }
}
