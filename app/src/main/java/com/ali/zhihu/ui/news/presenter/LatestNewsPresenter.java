package com.ali.zhihu.ui.news.presenter;


import android.util.Log;

import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.net.LatestNewsApi;
import com.ali.zhihu.ui.base.BaseObserver;
import com.ali.zhihu.ui.base.BasePresenter;
import com.ali.zhihu.ui.news.contract.LatestNewsContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Administrator on 2018/1/30.
 */

public class LatestNewsPresenter extends BasePresenter<LatestNewsContract.LastestNewView> implements LatestNewsContract.LastNewsPresenter {
    private static final String TAG = "LatestNewsPresenter";

    private LatestNewsApi latestNewsApi;
    @Inject
    public LatestNewsPresenter(LatestNewsApi latestNewsApi){
        this.latestNewsApi = latestNewsApi;
    }
    @Override
    public void getLatestNews() {

        Observable<LatestNews> observable = latestNewsApi.getLatestNews();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<LatestNews, List<LatestNews.StoriesBean>>() {
                    @Override
                    public List<LatestNews.StoriesBean> apply(LatestNews latestsNews) throws Exception{
                        List<LatestNews.StoriesBean> storiesBeenList = new ArrayList<>();
                        storiesBeenList = latestsNews.getStories();
                        mView.setDate(latestsNews.getDate());
                        mView.loadLatestView(storiesBeenList);
                        return storiesBeenList;

                    }
                })
                .subscribe(new BaseObserver<List<LatestNews.StoriesBean>>(){
                    @Override
                    public void onSuccsee(List<LatestNews.StoriesBean> s) {
                        Log.i(TAG,"success");
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.i(TAG,"fail");
                    }
                });
    }

    @Override
    public void getBeforeNews(String dateId) {
        latestNewsApi.getBeforeNews(dateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<LatestNews, List<LatestNews.StoriesBean>>(){
                    @Override
                    public List<LatestNews.StoriesBean> apply(LatestNews latestNews) throws Exception {
                        List<LatestNews.StoriesBean> beforeStoriesBeanList = latestNews.getStories();
                        mView.loadBeforeView(beforeStoriesBeanList);
                        return beforeStoriesBeanList;
                    }
                })
                .subscribe(new BaseObserver<List<LatestNews.StoriesBean>>() {
                    @Override
                    public void onSuccsee(List<LatestNews.StoriesBean> storiesBeen) {
                        Log.i(TAG,"success");
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.i(TAG,"fail");
                    }
                });
    }
}
