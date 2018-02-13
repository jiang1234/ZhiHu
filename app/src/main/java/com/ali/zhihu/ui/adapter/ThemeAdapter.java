package com.ali.zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.zhihu.R;
import com.ali.zhihu.bean.LatestNewsItem;
import com.ali.zhihu.bean.Theme;
import com.ali.zhihu.bean.ThemeItem;
import com.ali.zhihu.ui.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ThemeAdapter extends RecyclerView.Adapter {
    private int TYPE_HEADER = 0;
    private int TYPE_OTHER = 1;
    private List<ThemeItem> themeItemList;
    private View headerView;
    private Context context;

    public void setHeaderView(View headerView){
        this.headerView = headerView;
        notifyItemInserted(0);
        notifyItemRangeChanged(1,themeItemList.size());
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
        }
    }

    static class ThemeViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView news;
        ImageView image;
        public ThemeViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            news = cardView.findViewById(R.id.latest_news_text);
            image = cardView.findViewById(R.id.latest_news_image);
        }
    }

    public ThemeAdapter(List<ThemeItem> themeItemList,Context context){
        this.themeItemList = themeItemList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
           return new HeaderViewHolder(headerView);
        }
        View themeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_news_item,parent,false);
        final ThemeViewHolder themeViewHolder = new ThemeViewHolder(themeView);
        themeViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getRealPosition(themeViewHolder);
                int articleId = themeItemList.get(position).getId();
            }
        });
        return themeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(headerView != null && getItemViewType(position) == TYPE_HEADER){
            return;
        }
        int pos = getRealPosition(holder);
        ThemeItem themeItem = themeItemList.get(pos);
        ThemeViewHolder themeViewHolder = (ThemeViewHolder)holder;
        themeViewHolder.news.setText(themeItem.getTitle());
        if(themeItem.getImages() != null){
            ImageLoaderUtil.GlideImageLoader(context,themeItem.getImages(),themeViewHolder.image);
        }else{
            ImageLoaderUtil.GlideImageLoader(context,null,themeViewHolder.image);
        }

    }

    @Override
    public int getItemCount() {
        return headerView == null ? themeItemList.size() : themeItemList.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView != null && position == 0){
            return TYPE_HEADER;
        }
        return TYPE_OTHER;
    }

    private int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return headerView == null ? position :position - 1;
    }

    public int addTheme(List<Theme.Stories> storiesList){
        int oldItemNum = getItemCount();
        int storyId = storiesList.get(storiesList.size() - 1).getId();
        for(Theme.Stories stories : storiesList){
            if(stories.getImages() == null){
                ThemeItem themeItem = new ThemeItem(null,stories.getTitle(),stories.getId());
                themeItemList.add(themeItem);
            }else{
                ThemeItem themeItem = new ThemeItem(stories.getImages().get(0),stories.getTitle(),stories.getId());
                themeItemList.add(themeItem);
            }
        }
        if(oldItemNum == 0){
            notifyDataSetChanged();
        }else{
            notifyItemRangeInserted(0,storiesList.size());
            notifyItemRangeChanged(storiesList.size(),oldItemNum);
        }
        return storyId;
    }

    public int refreshTheme(List<Theme.Stories> storiesList, int endRefreshIndex){
        int oldItemNum = getItemCount();
        int storyId = themeItemList.get(themeItemList.size() - 1).getId();
        for(int i = 0; i <= endRefreshIndex; i++){
            Theme.Stories stories = storiesList.get(i);
            if(stories.getImages() == null){
                ThemeItem themeItem = new ThemeItem(null,stories.getTitle(),stories.getId());
                themeItemList.add(themeItem);
            }else{
                ThemeItem themeItem = new ThemeItem(stories.getImages().get(0),stories.getTitle(),stories.getId());
                themeItemList.add(themeItem);
            }
        }
        if(oldItemNum == 0){
            notifyDataSetChanged();
        }else{
            notifyItemRangeInserted(0,endRefreshIndex + 1);
            notifyItemRangeChanged(endRefreshIndex + 1,oldItemNum);
        }
        return storyId;
    }

    public int addBeforeTheme(List<Theme.Stories> storiesList){
        int oldItemNum = getItemCount();
        int storyId = storiesList.get(storiesList.size() - 1).getId();
        for(Theme.Stories stories : storiesList){
            if(stories.getImages() == null){
                ThemeItem themeItem = new ThemeItem(null,stories.getTitle(),stories.getId());
                themeItemList.add(themeItem);
            }else{
                ThemeItem themeItem = new ThemeItem(stories.getImages().get(0),stories.getTitle(),stories.getId());
                themeItemList.add(themeItem);
            }
        }
        notifyItemRangeChanged(oldItemNum,storiesList.size());
        return storyId;
    }
}
