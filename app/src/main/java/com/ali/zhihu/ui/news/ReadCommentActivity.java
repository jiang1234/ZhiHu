package com.ali.zhihu.ui.news;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ali.zhihu.R;
import com.ali.zhihu.bean.CommentItem;
import com.ali.zhihu.bean.LongComment;
import com.ali.zhihu.bean.ShortComment;
import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerHttpComponent;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.adapter.CommentAdapter;
import com.ali.zhihu.ui.base.BaseActivity;
import com.ali.zhihu.ui.news.contract.ReadCommentContract;
import com.ali.zhihu.ui.news.presenter.ReadCommentPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.ali.zhihu.ui.news.ReadArticleActivity.ARTICLEID;

public class ReadCommentActivity extends BaseActivity<ReadCommentPresenter> implements ReadCommentContract.ReadCommentView{
    public final static String READ_COMMENT = "READ_COMMENT";
    private RecyclerView recyclerView;
    private String articleId;
    private CommentAdapter commentAdapter;
    private List<CommentItem> commentItemList;
    private Toolbar toolbar;
    @Override
    public int getLayout() {
        return R.layout.activity_read_comment;
    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(applicationComponent)
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void initView() {
        commentItemList = new ArrayList<>();
        toolbar = (Toolbar)findViewById(R.id.comment_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        recyclerView = (RecyclerView)findViewById(R.id.comment_recycler_view);
        commentAdapter = new CommentAdapter(commentItemList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initDate() {
        Intent intent = getIntent();
        articleId = intent.getStringExtra(READ_COMMENT);
        presenter.getLongComment(articleId);
        presenter.getShortComment(articleId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void readLongComment(List<LongComment.Comment> longComment) {
        //commentItemList.add()
        CommentItem commentItem0 = new CommentItem(longComment.size(),CommentItem.TYPE_LONG);
        commentItemList.add(commentItem0);
        if(!longComment.isEmpty()){
            for(LongComment.Comment comment : longComment){
                CommentItem commentItem = new CommentItem(comment.getAuthor(),comment.getAvatar(),comment.getContent(),comment.getTime(),CommentItem.LONG_COMMENT);
                if(comment.getReply_to() != null){
                    commentItem.setReplyToComment(comment.getReply_to().getContent());
                    commentItem.setReplyToId(comment.getReply_to().getId());
                    commentItem.setReplyToStatus(comment.getReply_to().getStatus());
                }
                commentItemList.add(commentItem);
            }
        }

        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void readShortComment(List<ShortComment.Comment> shortComment) {
        CommentItem commentItem0 = new CommentItem(CommentItem.TYPE_SHORT);
        commentItem0.setShortCommentNum(shortComment.size());
        commentItemList.add(commentItem0);
        if(!shortComment.isEmpty()){
            for(ShortComment.Comment comment : shortComment){
                CommentItem commentItem = new CommentItem(comment.getAuthor(),comment.getAvatar(),comment.getContent(),comment.getTime(),CommentItem.SHORT_COMMENT);
                commentItemList.add(commentItem);
            }
        }

        commentAdapter.notifyDataSetChanged();
    }
}
