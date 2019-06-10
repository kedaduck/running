package com.ding.running.Activity.scenic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ding.running.Activity.scenic.HotelActivity;
import com.ding.running.Activity.scenic.adapter.holder.HotelHolder;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.vo.HotelVo;

import java.util.List;

/**
 * @ClassName HotelAdapter
 * @Author Leoren
 * @Date 2019/5/6 16:29
 * Description :
 * @Version v1.0
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelHolder> {

    private List<HotelVo> hotelVoList;
    private Context context;

    public HotelAdapter(List<HotelVo> hotelVoList, Context context) {
        this.hotelVoList = hotelVoList;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_layout, parent, false);
        HotelHolder holder = new HotelHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, int i) {
        final HotelVo vo = hotelVoList.get(i);
        Log.e("Leoren", vo.getPicture());
        Glide.with(context).load(vo.getPicture()).into(holder.hotelImg);
        holder.hotelNameText.setText(vo.getName());
        holder.hotelDetailText.setText(vo.getText());
        holder.hotelTypeText.setText(vo.getType());
        holder.hotelPriceText.setText("¥" + vo.getPrice());
        holder.itemHotelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HotelActivity.class);
                intent.putExtra("content", GsonUtil.formatBeanToJSON(vo));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return hotelVoList.size();
    }
}
