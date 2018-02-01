package com.ali.zhihu.ui.news.contract;

import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.ui.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface LatestNewsContract {
    interface LastNewsPresenter extends BaseContract.BasePresenter<LastestNewView>{
        void getLatestNews();

        void getBeforeNews(String dateId);

    }
    interface LastestNewView extends BaseContract.BaseView{
        void loadLatestView(List<LatestNews.StoriesBean> latestNewsStoriesBean);

        void loadBeforeView(List<LatestNews.StoriesBean> latestNewsStoriesBean);

        void setDate(String date);
    }
}
