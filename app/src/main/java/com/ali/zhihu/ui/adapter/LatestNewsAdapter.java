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
import com.ali.zhihu.ui.util.ImageLoaderUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2018/1/31.
 */

public class LatestNewsAdapter extends RecyclerView.Adapter {
    private List<LatestNewsItem> mlatestNewsItemList;

    public final static int TYPE_NEW = 0;
    public final static int TYPE_DATE = 1;
    public final static int TYPE_HEADER = 2;

    private View headerView;

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

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
        if(headerView != null && viewType == TYPE_HEADER){
            return new HeaderViewHolder(headerView);
        }else if(viewType == TYPE_DATE){
            View dateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_date_item,parent,false);
            return new DateViewHolder(dateView);
        }else{
            View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_news_item,parent,false);
            return new NewViewHolder(newView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(headerView != null && getItemViewType(position) == TYPE_HEADER){
            return;
        } else if(getItemViewType(position) == TYPE_DATE){
            int pos = getRealPosition(holder);
            LatestNewsItem latestNewsItem = mlatestNewsItemList.get(pos);
            DateViewHolder dateViewHolder = (DateViewHolder)holder;
            if(DateUtil.isSystemDate(latestNewsItem.getDate())){
                dateViewHolder.date.setText(R.string.latest_date_text);
            }else{
                dateViewHolder.date.setText(DateUtil.changeFormat(latestNewsItem.getDate()));
            }
        }else{
            int pos = getRealPosition(holder);
            LatestNewsItem latestNewsItem = mlatestNewsItemList.get(pos);
            NewViewHolder newViewHolder = (NewViewHolder)holder;
            newViewHolder.news.setText(latestNewsItem.getNews());
            ImageLoaderUtil.GlideImageLoader(MyApplication.getContext(),latestNewsItem.getImageId(),newViewHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return headerView == null ? mlatestNewsItemList.size() : mlatestNewsItemList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView == null){
            if(!mlatestNewsItemList.isEmpty() && mlatestNewsItemList.get(position).getType() == LatestNewsItem.TYPE_DATE){
                return TYPE_DATE;
            }
            else{
                return TYPE_NEW;
            }
        }else{
            if(position == 0){
                return TYPE_HEADER;
            }else if(!mlatestNewsItemList.isEmpty() && mlatestNewsItemList.get(position - 1).getType() == LatestNewsItem.TYPE_DATE){
                return TYPE_DATE;
            }
            else{
                return TYPE_NEW;
            }
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return headerView == null ? position :position - 1;
    }
}
