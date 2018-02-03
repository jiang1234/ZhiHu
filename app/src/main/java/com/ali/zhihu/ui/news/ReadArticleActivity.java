package com.ali.zhihu.ui.news;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ali.zhihu.R;
import com.ali.zhihu.bean.Article;
import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerHttpComponent;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.base.BaseActivity;
import com.ali.zhihu.ui.news.contract.ReadArticleContract;
import com.ali.zhihu.ui.news.presenter.ReadArticlePresenter;
import com.ali.zhihu.ui.util.HtmlUtil;
import com.ali.zhihu.ui.util.ImageLoaderUtil;

public class ReadArticleActivity extends BaseActivity<ReadArticlePresenter> implements ReadArticleContract.ReadArticleView {
    public static final String ARTICLEID = "ARTICLEID";
    private WebView webView;
    private String articleId;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView titleImageView;


    @Override
    public int getLayout() {
        return R.layout.read_article_layout;
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
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        titleImageView = (ImageView)findViewById(R.id.title_image);

        //ActionBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
        articleId = intent.getStringExtra(ARTICLEID);
        presenter.getArticle(articleId);
    }

    @Override
    public void readOtherArticle(Article article) {
        collapsingToolbar.setTitle(article.getTitle());
        //ImageLoaderUtil.GlideImageLoader(this,article.getImage(),titleImageView);
        webView.loadData(HtmlUtil.createHtmlData(article),HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);
    }

    @Override
    public void readZhihuArticle(Article article) {
        collapsingToolbar.setTitle(article.getTitle());
        ImageLoaderUtil.GlideImageLoader(this,article.getImage(),titleImageView);
        webView.loadData(HtmlUtil.createHtmlData(article),HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);
    }
}
