package com.ali.zhihu.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.zhihu.MyApplication;
import com.ali.zhihu.R;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.bean.LatestNewsItem;
import com.ali.zhihu.ui.util.DateUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class LatestNewsAdapter extends RecyclerView.Adapter {
    private List<LatestNewsItem> mlatestNewsItemList;

    public final static int TYPE_NEW = 0;

    public final static int TYPE_DATE = 1;

    public final static int TYPE_EMPTY = 2;

    static class NewViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView news;
        ImageView image;
        public NewViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            news = cardView.findViewById(R.id.latest_news_text);
            image = cardView.findViewById(R.id.latest_news_image);
        }
    }

    static class DateViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        public DateViewHolder(View itewView){
            super(itewView);
            date = itewView.findViewById(R.id.news_date);
        }
    }

    public LatestNewsAdapter(List<LatestNewsItem> mlatestNewsItemList){
        this.mlatestNewsItemList = mlatestNewsItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_DATE){
            View dateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_date_item,parent,false);
            return new DateViewHolder(dateView);
        }else{
            View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_news_item,parent,false);
            return new NewViewHolder(newView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LatestNewsItem latestNewsItem = mlatestNewsItemList.get(position);
        if(getItemViewType(position) == TYPE_DATE){
            DateViewHolder dateViewHolder = (DateViewHolder)holder;
            if(DateUtil.isSystemDate(latestNewsItem.getDate())){
                dateViewHolder.date.setText(R.string.latest_date_text);
            }else{
                dateViewHolder.date.setText(DateUtil.changeFormat(latestNewsItem.getDate()));
            }
        }else{
            NewViewHolder newViewHolder = (NewViewHolder)holder;
            newViewHolder.news.setText(latestNewsItem.getNews());
            Glide.with(MyApplication.getContext())
                    .load(latestNewsItem.getImageId())
                    .into(newViewHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return mlatestNewsItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(!mlatestNewsItemList.isEmpty() && mlatestNewsItemList.get(position).getType() == LatestNewsItem.TYPE_DATE){
            return TYPE_DATE;
        }
        else if(!mlatestNewsItemList.isEmpty() && mlatestNewsItemList.get(position).getType() == LatestNewsItem.TYPE_NEW){
            return TYPE_NEW;
        }else{
            return TYPE_EMPTY;
        }
    }
}
