package com.guaju.adoulive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.guaju.adoulive.bean.HomeInfo;

import java.util.ArrayList;

/**
 * Created by guaju on 2018/1/11.
 */

public class HomeRoomInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<HomeInfo> mList;

    public HomeRoomInfoAdapter(Context context, ArrayList<HomeInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HomeInfoViewHolder extends  RecyclerView.ViewHolder{


        public HomeInfoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
