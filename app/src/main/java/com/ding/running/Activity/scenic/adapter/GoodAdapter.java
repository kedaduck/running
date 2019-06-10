package com.ding.running.Activity.scenic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ding.running.Activity.scenic.GoodDetailActivity;
import com.ding.running.Activity.scenic.GoodShowActivity;
import com.ding.running.Activity.scenic.adapter.holder.GoodHolder;
import com.ding.running.R;
import com.ding.running.Utils.GsonUtil;
import com.ding.running.vo.GoodVo;

import java.util.List;

/**
 * @ClassName GoodAdapter
 * @Author Leoren
 * @Date 2019/5/6 16:08
 * Description :
 * @Version v1.0
 */
public class GoodAdapter extends RecyclerView.Adapter<GoodHolder> {

    private List<GoodVo> goodVoList;
    private Context context;

    public GoodAdapter(List<GoodVo> goodVoList, Context context) {
        this.goodVoList = goodVoList;
        this.context = context;
    }

    @NonNull
    @Override
    public GoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_good_layout, parent, false);
        GoodHolder holder = new GoodHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodHolder holder, int i) {
        final GoodVo vo = goodVoList.get(i);
        Glide.with(context).load(vo.getPicture()).into(holder.goodImg);
        holder.goodTitleText.setText(vo.getName());
        holder.goodDetailText.setText(vo.getText());
        holder.storeNameText.setText(vo.getStorename());
        holder.priceText.setText("¥" + vo.getPrice());
        holder.itemGoodView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodShowActivity.class);
                intent.putExtra("content", GsonUtil.formatBeanToJSON(vo));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodVoList.size();
    }
}
