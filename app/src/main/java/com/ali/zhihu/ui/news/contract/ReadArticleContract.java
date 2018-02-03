package com.ali.zhihu.ui.news.contract;

import com.ali.zhihu.bean.Article;
import com.ali.zhihu.ui.base.BaseContract;

/**
 * Created by Administrator on 2018/2/3.
 */

public interface ReadArticleContract {
    interface ReadArticlePresenter extends BaseContract.BasePresenter<ReadArticleView>{
        void getArticle(String articleId);
    }
    interface ReadArticleView extends BaseContract.BaseView{
        void readZhihuArticle(Article article);
        void readOtherArticle(Article article);
    }
}
