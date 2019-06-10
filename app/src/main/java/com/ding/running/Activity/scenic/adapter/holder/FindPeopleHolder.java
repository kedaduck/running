package com.ding.running.Activity.scenic.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.running.R;

import org.w3c.dom.Text;

/**
 * @ClassName FindPeopleHolder
 * @Author Leoren
 * @Date 2019/5/19 11:22
 * Description :
 * @Version v1.0
 */
public class FindPeopleHolder extends RecyclerView.ViewHolder {

    public LinearLayout itemFindView;
    public ImageView img;
    public TextView title;
    public TextView detail;
    public TextView time;




    public FindPeopleHolder(@NonNull View itemView) {
        super(itemView);
        itemFindView = (LinearLayout) itemView;
        img = itemView.findViewById(R.id.item_find_people_img);
        title = itemView.findViewById(R.id.item_find_people_title);
        detail = itemView.findViewById(R.id.item_find_people_detail);
        time = itemView.findViewById(R.id.item_find_people_time);
    }
}
