package com.ali.zhihu.ui.theme;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.ali.zhihu.R;
import com.ali.zhihu.bean.Article;
import com.ali.zhihu.bean.ReadTheme;
import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerHttpComponent;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.base.BaseActivity;
import com.ali.zhihu.ui.base.BasePresenter;
import com.ali.zhihu.ui.news.contract.ReadArticleContract;
import com.ali.zhihu.ui.news.presenter.ReadArticlePresenter;
import com.ali.zhihu.ui.util.HtmlUtil;

/**
 * Created by Administrator on 2018/2/13.
 */

public class ReadThemeActivity extends BaseActivity<ReadThemePresenter> implements ReadThemeContract.ReadThemeView {
    public static final String ARTICLEID = "ARTICLEID";
    private WebView webView;
    private int articleId;
    private Toolbar toolbar;

    @Override
    public int getLayout() {
        return R.layout.read_theme;
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
        articleId = intent.getIntExtra(ARTICLEID,'0');
        presenter.getArticle(articleId);
    }

    @Override
    public void readZhihuArticle(ReadTheme article) {
        webView.loadData(HtmlUtil.createHtmlData(article),HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);
    }

    @Override
    public void readOtherArticle(ReadTheme article) {
        webView.loadData(HtmlUtil.createHtmlData(article),HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);
    }
}
