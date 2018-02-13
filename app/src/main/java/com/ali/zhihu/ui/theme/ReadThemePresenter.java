package com.ali.zhihu.ui.theme;

import com.ali.zhihu.bean.Article;
import com.ali.zhihu.bean.ReadTheme;
import com.ali.zhihu.net.ThemeApi;
import com.ali.zhihu.ui.base.BaseObserver;
import com.ali.zhihu.ui.base.BasePresenter;
import com.ali.zhihu.ui.news.contract.ReadArticleContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/13.
 */

public class ReadThemePresenter extends BasePresenter<ReadThemeContract.ReadThemeView> implements ReadThemeContract.ReadThemePresenter {
    ThemeApi themeApi;

    @Inject
    public ReadThemePresenter(ThemeApi themeApi) {
        this.themeApi = themeApi;
    }

    @Override
    public void getArticle(int articleId) {
        themeApi.readTheme(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ReadTheme>() {
                    @Override
                    public void onSuccsee(ReadTheme article) {
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
