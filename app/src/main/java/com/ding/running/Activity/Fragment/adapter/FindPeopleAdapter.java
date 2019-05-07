package com.ding.running.Activity.Fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ding.running.Activity.FindPeople.FindPeopleDetail;
import com.ding.running.Application.MyApplication;
import com.ding.running.Common.Const;
import com.ding.running.R;
import com.ding.running.Utils.DateParseUtil;
import com.ding.running.vo.FindPeopleVo;

import java.util.List;

/**
 * @ClassName FindPeopleAdapter
 * @Author Leoren
 * @Date 2019/5/6 10:21
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
    public void onBindViewHolder(@NonNull FindPeopleHolder holder, int position) {
        final FindPeopleVo vo = findPeopleVoList.get(position);
        Glide.with(context).load(vo.getPicture()).into(holder.headImg);
        holder.titleText.setText(vo.getTitle());
        holder.detailText.setText(vo.getText());
        holder.timeText.setText(DateParseUtil.parseData(vo.getPublishTime()));
        holder.itemFindPeopleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindPeopleDetail.class);
                intent.putExtra(Const.FINDPEOPLEITEM, vo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return findPeopleVoList.size();
    }
}
