package com.ali.zhihu.ui.news.presenter;

import com.ali.zhihu.bean.Article;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.net.LatestNewsApi;
import com.ali.zhihu.ui.base.BaseObserver;
import com.ali.zhihu.ui.base.BasePresenter;
import com.ali.zhihu.ui.news.contract.ReadArticleContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/3.
 */

public class ReadArticlePresenter extends BasePresenter<ReadArticleContract.ReadArticleView> implements ReadArticleContract.ReadArticlePresenter {
    LatestNewsApi latestNewsApi;
    @Inject
    public ReadArticlePresenter(LatestNewsApi latestNewApi){
        this.latestNewsApi = latestNewApi;
    }
    @Override
    public void getArticle(String articleId) {
        latestNewsApi.readArticle(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Article>() {
                    @Override
                    public void onSuccsee(Article article) {
                        //知乎文章
                        if(article.getType() == 0){
                            mView.readZhihuArticle(article);
                        }else{
                            //站外文章
                            mView.readOtherArticle(article);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }
}
