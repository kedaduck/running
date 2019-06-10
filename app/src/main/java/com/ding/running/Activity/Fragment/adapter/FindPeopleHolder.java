package com.ding.running.Activity.Fragment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.running.R;

/**
 * @ClassName FindPeopleHolder
 * @Author Leoren
 * @Date 2019/5/6 10:21
 * Description :
 * @Version v1.0
 */
public class FindPeopleHolder extends RecyclerView.ViewHolder {

    public LinearLayout itemFindPeopleView;
    public ImageView headImg;
    public TextView titleText;
    public TextView detailText;
    public TextView timeText;
    public Button findBtn;



    public FindPeopleHolder(@NonNull View itemView) {
        super(itemView);
        itemFindPeopleView = (LinearLayout) itemView;
        headImg = itemView.findViewById(R.id.item_find_people_img);
        titleText = itemView.findViewById(R.id.item_find_people_title);
        detailText = itemView.findViewById(R.id.item_find_people_detail);
        timeText = itemView.findViewById(R.id.item_find_people_time);
        findBtn = itemView.findViewById(R.id.item_find_people_btn);
    }
}
