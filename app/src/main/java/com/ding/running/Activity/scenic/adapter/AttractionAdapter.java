package com.ding.running.Activity.scenic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ding.running.Activity.scenic.adapter.holder.AttractionHolder;
import com.ding.running.Application.MyApplication;
import com.ding.running.R;
import com.ding.running.vo.AttractionVo;

import java.util.List;
import java.util.Locale;

/**
 * @ClassName AttractionAdapter
 * @Author Leoren
 * @Date 2019/5/6 12:06
 * Description :
 * @Version v1.0
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionHolder> {

    private List<AttractionVo> attractionVoList;
    private Context context;
    private TextToSpeech tts;

    public AttractionAdapter(List<AttractionVo> attractionVoList, Context context) {
        this.attractionVoList = attractionVoList;
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == tts.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if(result < 0) {
                        tts.setLanguage(Locale.CHINA);
                    }

                }
            }
        });
    }

    @NonNull
    @Override
    public AttractionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context == null){
            context = MyApplication.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_attraction_layout, viewGroup, false);
        AttractionHolder holder = new AttractionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionHolder holder, int i) {
        final AttractionVo vo = attractionVoList.get(i);
        Glide.with(context).load(vo.getPicture()).into(holder.attractionImg);
        holder.attractionNameText.setText(vo.getAttractioname());
        holder.attractionDetailText.setText(vo.getText());
        holder.itemAttractionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak("当前所在景点" + vo.getAttractioname()+ "所在地址" + vo.getText(),
                        TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractionVoList.size();
    }
}
