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
import com.ali.zhihu.widget.ExpandableTextView;
import com.ali.zhihu.widget.JustifyTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/4.
 */

public class CommentAdapter extends RecyclerView.Adapter {
    public static final int LONG_COMMENT = 0;
    public static final int SHORT_COMMENT = 1;
    public static final int TYPE_LONG = 2;
    public static final int TYPE_SHORT = 3;
    private List<CommentItem> commentItemList;
    private Context context;

    public CommentAdapter(List<CommentItem> commentItemList, Context context) {
        this.commentItemList = commentItemList;
        this.context = context;
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView userIdTextView;
        JustifyTextView commentTextView;
        TextView timeTextView;
        ExpandableTextView expandableTextView;
        public CommentViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.user_image);
            userIdTextView = (TextView) itemView.findViewById(R.id.user_id);
            commentTextView = (JustifyTextView) itemView.findViewById(R.id.comment);
            timeTextView = (TextView) itemView.findViewById(R.id.time);
            expandableTextView = (ExpandableTextView)itemView.findViewById(R.id.expandable_text_view);

        }
    }

    static class ShortCommentViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView userIdTextView;
        JustifyTextView commentTextView;
        TextView timeTextView;
        public ShortCommentViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.user_image);
            userIdTextView = (TextView) itemView.findViewById(R.id.user_id);
            commentTextView = (JustifyTextView) itemView.findViewById(R.id.comment);
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
        if(commentItemList.get(position).getType() == TYPE_LONG){
            return TYPE_LONG;
        }else if(commentItemList.get(position).getType() == TYPE_SHORT){
            return TYPE_SHORT;
        }else if(commentItemList.get(position).getType() == LONG_COMMENT){
            return LONG_COMMENT;
        }else{
            return SHORT_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_LONG){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false);
            TypeViewHolder typeViewHolder = new TypeViewHolder(view);
            return typeViewHolder;
        }else if(viewType == TYPE_SHORT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false);
            TypeViewHolder typeViewHolder = new TypeViewHolder(view);
            return typeViewHolder;
        }else if(viewType == LONG_COMMENT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
            CommentViewHolder commentViewHolder = new CommentViewHolder(view);
            return commentViewHolder;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.short_comment_item,parent,false);
            ShortCommentViewHolder commentViewHolder = new ShortCommentViewHolder(view);
            return commentViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_LONG){
            ((TypeViewHolder)holder).typeTextView.setText(commentItemList.get(position).getLongCommentNum()+"条长评");
        }else if(getItemViewType(position) == TYPE_SHORT){
            ((TypeViewHolder)holder).typeTextView.setText(commentItemList.get(position).getShortCommentNum()+"条短评");
        }else if(getItemViewType(position) == LONG_COMMENT){
            CommentViewHolder commentViewHolder = (CommentViewHolder)holder;
            ImageLoaderUtil.GlideImageLoader(context,commentItemList.get(position).getUserImage(),commentViewHolder.imageView);
            commentViewHolder.commentTextView.setText(commentItemList.get(position).getComment());
            commentViewHolder.timeTextView.setText(commentItemList.get(position).getTime());
            commentViewHolder.userIdTextView.setText(commentItemList.get(position).getUserId());
            commentViewHolder.expandableTextView.setExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
                @Override
                public void onExpandStateChanged(boolean collapse) {
                    commentItemList.get(position).setCollapsed(collapse);
                }
            });
            if(commentItemList.get(position).getReplyToComment() != null){
                if(commentItemList.get(position).getReplyToStatus() != 0){
                    commentViewHolder.expandableTextView.setText("抱歉，原点评已经删除", commentItemList.get(position).isCollapsed());
                }else{
                    String text = "//" + commentItemList.get(position).getReplyToId() + " : " + commentItemList.get(position).getReplyToComment();
                    commentViewHolder.expandableTextView.setText(text, commentItemList.get(position).isCollapsed());
                }
            }else{
                commentViewHolder.expandableTextView.setText("", commentItemList.get(position).isCollapsed());
            }
        }else{
            ShortCommentViewHolder commentViewHolder = (ShortCommentViewHolder)holder;
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
