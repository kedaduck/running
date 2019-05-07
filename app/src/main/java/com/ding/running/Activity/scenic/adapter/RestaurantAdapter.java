package com.ding.running.Activity.scenic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ding.running.Activity.scenic.adapter.holder.RestaurantHolder;
import com.ding.running.R;
import com.ding.running.vo.RestaurantVo;

import java.util.List;

/**
 * @ClassName RestaurantAdapter
 * @Author Leoren
 * @Date 2019/5/6 16:50
 * Description :
 * @Version v1.0
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantHolder> {

    private List<RestaurantVo> restaurantVoList;
    private Context context;

    public RestaurantAdapter(List<RestaurantVo> restaurantVoList, Context context) {
        this.restaurantVoList = restaurantVoList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_layout, parent, false);
        RestaurantHolder holder = new RestaurantHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int i) {
        RestaurantVo vo = restaurantVoList.get(i);
        Glide.with(context).load(vo.getPicture()).into(holder.restaurantImg);
        holder.restaurantNameText.setText(vo.getName());
        holder.restaurantDetailText.setText(vo.getText());
        holder.restaurantPriceText.setText("¥" + vo.getPrice());
    }

    @Override
    public int getItemCount() {
        return restaurantVoList.size();
    }
}
