package com.ali.zhihu.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.ali.zhihu.ui.news.ReadArticleActivity;
import com.ali.zhihu.ui.util.DateUtil;
import com.ali.zhihu.ui.util.ImageLoaderUtil;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.util.Date;
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
    private Context context;

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

    public LatestNewsAdapter(List<LatestNewsItem> mlatestNewsItemList,Context context){
        this.mlatestNewsItemList = mlatestNewsItemList;
        this.context = context;
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
            final NewViewHolder newViewHolder = new NewViewHolder(newView);
            newViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posotion = getRealPosition(newViewHolder);
                    int articleId = mlatestNewsItemList.get(posotion).getId();
                    toReadInRecycler(articleId);
                }
            });
            return newViewHolder;
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
            ImageLoaderUtil.GlideImageLoader(context,latestNewsItem.getImageId(),newViewHolder.image);
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

    public void toReadInRecycler(int articleId){
        Intent intent = new Intent(context, ReadArticleActivity.class);
        intent.putExtra(ReadArticleActivity.ARTICLEID,articleId);
        context.startActivity(intent);
    }

    public void addNew(List<LatestNews.StoriesBean> storiesBeanList, String date){
        int oldItemNum = getItemCount();
        LatestNewsItem latestNewsItem0= new LatestNewsItem(date,LatestNewsItem.TYPE_DATE);
        mlatestNewsItemList.add(0,latestNewsItem0);
        int i = 1;
        //int i = 0;
        for(LatestNews.StoriesBean storiesBean : storiesBeanList){
            LatestNewsItem latestNewsItem= new LatestNewsItem(storiesBean.getTitle(),storiesBean.getImages().get(0),LatestNewsItem.TYPE_NEW,storiesBean.getId());
            mlatestNewsItemList.add(i,latestNewsItem);
            i++;
        }
        if(oldItemNum == 0){
            notifyDataSetChanged();
        }else{
            notifyItemRangeInserted(0,storiesBeanList.size() + 1);
            notifyItemRangeChanged(storiesBeanList.size() + 1,oldItemNum);
        }

    }
    public void refreshNew(List<LatestNews.StoriesBean> storiesBeanList, String date, int endRefreshIndex){
        int oldItemNum = getItemCount();
        if(!DateUtil.isSystemDate(date)){
            LatestNewsItem latestNewsItem0= new LatestNewsItem(date,LatestNewsItem.TYPE_DATE);
            mlatestNewsItemList.add(0,latestNewsItem0);
            }

        for(int i = 0; i <= endRefreshIndex; i++){
            int j = 1;
            LatestNews.StoriesBean storiesBean = storiesBeanList.get(i);
            LatestNewsItem latestNewsItem= new LatestNewsItem(storiesBean.getTitle(),storiesBean.getImages().get(0),LatestNewsItem.TYPE_NEW,storiesBean.getId());
            mlatestNewsItemList.add(j,latestNewsItem);
            j++;
        }

        if(oldItemNum == 0){
            notifyDataSetChanged();
        }else{
            if(!DateUtil.isSystemDate(date)){
                notifyItemRangeInserted(0,endRefreshIndex + 2);
                notifyItemRangeChanged(endRefreshIndex + 2,oldItemNum);
            }else{
                notifyItemRangeInserted(1,endRefreshIndex + 1);
                notifyItemRangeChanged(endRefreshIndex + 1,oldItemNum);
            }

        }

    }
}
