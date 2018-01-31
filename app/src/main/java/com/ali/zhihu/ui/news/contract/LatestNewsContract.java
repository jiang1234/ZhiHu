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

    }
    interface LastestNewView extends BaseContract.BaseView{
        void loadView(List<LatestNews.StoriesBean> latestNewsStoriesBean);

    }
}
