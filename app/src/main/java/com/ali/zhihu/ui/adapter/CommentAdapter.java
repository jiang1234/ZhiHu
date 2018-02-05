package com.ali.zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.zhihu.R;
import com.ali.zhihu.bean.CommentItem;
import com.ali.zhihu.ui.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */

public class CommentAdapter extends RecyclerView.Adapter {
    public static final int LONG_COMMENT = 0;
    public static final int SHORT_COMMENT = 1;
    public static final int TYPE = 2;
    private List<CommentItem> commentItemList;
    private Context context;

    public CommentAdapter(List<CommentItem> commentItemList, Context context) {
        this.commentItemList = commentItemList;
        this.context = context;
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView userIdTextView;
        TextView commentTextView;
        TextView timeTextView;
        public CommentViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.user_image);
            userIdTextView = (TextView) itemView.findViewById(R.id.user_id);
            commentTextView = (TextView) itemView.findViewById(R.id.comment);
            timeTextView = (TextView) itemView.findViewById(R.id.time);
        }
    }

    static class TypeViewHolder extends RecyclerView.ViewHolder{
        TextView typeTextView;
        public TypeViewHolder(View itemView) {
            super(itemView);
            typeTextView = (TextView) itemView.findViewById(R.id.type_comment);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(commentItemList.get(position).getType() == TYPE){
            return TYPE;
        }else if(commentItemList.get(position).getType() == LONG_COMMENT){
            return LONG_COMMENT;
        }else{
            return SHORT_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false);
            TypeViewHolder typeViewHolder = new TypeViewHolder(view);
            return typeViewHolder;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
            CommentViewHolder commentViewHolder = new CommentViewHolder(view);
            return commentViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE){
            ((TypeViewHolder)holder).typeTextView.setText("aa");
        }else{
            CommentViewHolder commentViewHolder = (CommentViewHolder)holder;
            ImageLoaderUtil.GlideImageLoader(context,commentItemList.get(position).getUserImage(),commentViewHolder.imageView);
            commentViewHolder.commentTextView.setText(commentItemList.get(position).getComment());
            commentViewHolder.timeTextView.setText(commentItemList.get(position).getTime());
            commentViewHolder.userIdTextView.setText(commentItemList.get(position).getUserId());
        }
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }
}
