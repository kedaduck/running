package com.ding.running.Activity.scenic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ding.running.Activity.scenic.adapter.holder.AttractionHolder;
import com.ding.running.Activity.scenic.adapter.holder.FindPeopleHolder;
import com.ding.running.Application.MyApplication;
import com.ding.running.R;
import com.ding.running.Utils.DateParseUtil;
import com.ding.running.vo.AttractionVo;
import com.ding.running.vo.FindPeopleVo;

import java.util.List;

/**
 * @ClassName FindPeopleAdapter
 * @Author Leoren
 * @Date 2019/5/19 11:22
 * Description :
 * @Version v1.0
 */
public class FindPeopleAdapter extends RecyclerView.Adapter<FindPeopleHolder> {

    private List<FindPeopleVo> findPeopleVoList;
    private Context context;

    public FindPeopleAdapter(List<FindPeopleVo> findPeopleVoList, Context context) {
        this.findPeopleVoList = findPeopleVoList;
        this.context = context;
    }

    @NonNull
    @Override
    public FindPeopleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context == null){
            context = MyApplication.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_find_people, viewGroup, false);
        FindPeopleHolder holder = new FindPeopleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindPeopleHolder holder, int i) {
        FindPeopleVo vo = findPeopleVoList.get(i);
        Glide.with(context).load(vo.getPicture()).into(holder.img);
        holder.title.setText(vo.getTitle());
        holder.detail.setText(vo.getText());
        holder.time.setText(vo.getEndTime());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
