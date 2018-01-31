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
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ViewHolder> {
    private List<LatestNews.StoriesBean> mlatestNewsStoriesBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView news;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            news = cardView.findViewById(R.id.latest_news_text);
            image = cardView.findViewById(R.id.latest_news_image);
        }
    }

    public LatestNewsAdapter(List<LatestNews.StoriesBean> mlatestNewsStoriesBeanList){
        this.mlatestNewsStoriesBeanList = mlatestNewsStoriesBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LatestNews.StoriesBean latestNewsStoriesBean = mlatestNewsStoriesBeanList.get(position);
        holder.news.setText(latestNewsStoriesBean.getTitle());
        Glide.with(MyApplication.getContext())
                .load(latestNewsStoriesBean.getImages().get(0))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mlatestNewsStoriesBeanList.size();
    }
}
