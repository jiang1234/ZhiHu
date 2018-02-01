package com.ali.zhihu.ui.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ali.zhihu.R;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.bean.LatestNewsItem;
import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerHttpComponent;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.adapter.LatestNewsAdapter;
import com.ali.zhihu.ui.base.BaseFragment;
import com.ali.zhihu.ui.news.contract.LatestNewsContract;
import com.ali.zhihu.ui.news.presenter.LatestNewsPresenter;
import com.ali.zhihu.ui.util.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2018/1/30.
 * SwipeRefreshLayout中只能有一个RecyclerView控件 否则会无法更新RecyclerView
 */

public class LastestNewsFragment extends BaseFragment<LatestNewsPresenter> implements LatestNewsContract.LastestNewView{
    public static final String TAG = "LastestNewsFragment";
    private LatestNewsAdapter latestNewsAdapter;
    private RecyclerView latestNewsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textView;
    private List<LatestNews.StoriesBean> latestNewsStoriesBeanList;
    private List<LatestNewsItem> latestNewsItemsList;
    private boolean isLoadMore;
    private boolean isRefresh;
    private String date;
    //用于访问以前的新闻
    private int beforeDays;
    //访问11月20日的 请求为1121
    private String beforeDate;
    //请求为1121 实际为1120
    private String berforeDateShow;

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_news_latest;
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

        latestNewsItemsList = new ArrayList<>();
        //textView = (TextView) getActivity().findViewById(R.id.get_news);
        swipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh);
        latestNewsRecyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view_latest_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        latestNewsRecyclerView.setLayoutManager(layoutManager);
        latestNewsAdapter = new LatestNewsAdapter(latestNewsItemsList);
        latestNewsRecyclerView.setAdapter(latestNewsAdapter);
        latestNewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!isLoadMore && !latestNewsRecyclerView.canScrollVertically(1)){
                    Log.i(TAG,"loadmore"+beforeDays);
                    isLoadMore = true;
                    beforeDate = DateUtil.Before(beforeDays,date);
                    berforeDateShow = DateUtil.Before(1,beforeDate);
                    presenter.getBeforeNews(beforeDate);
                    //isLoadMore = false;
                    //不能将isLoadMore = false;放在此处，有可能会在网络请求返回刷新页面前调用，从而刷新两次
                }
            }
        });


        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    isRefresh = true;
                    refreshNews();
                }
            }
        });
    }

    @Override
    public void loadLatestView(List<LatestNews.StoriesBean> storiesBeanList) {
        Log.i(TAG,"loadLatestView");
        int oldItemNum = latestNewsAdapter.getItemCount();
        LatestNewsItem latestNewsItem0= new LatestNewsItem(date,LatestNewsItem.TYPE_DATE);
        latestNewsItemsList.add(0,latestNewsItem0);
        int i = 1;
        for(LatestNews.StoriesBean storiesBean : storiesBeanList){
            LatestNewsItem latestNewsItem= new LatestNewsItem(storiesBean.getTitle(),storiesBean.getImages().get(0),LatestNewsItem.TYPE_NEW);
            latestNewsItemsList.add(i,latestNewsItem);
            i++;
        }
        if(oldItemNum == 0){
            latestNewsAdapter.notifyDataSetChanged();
        }else{
            latestNewsAdapter.notifyItemRangeInserted(0,storiesBeanList.size() + 1);
            latestNewsAdapter.notifyItemRangeChanged(storiesBeanList.size() + 1,oldItemNum);
        }
        isRefresh = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadBeforeView(List<LatestNews.StoriesBean> beforeStoriesBeanList) {
        Log.i(TAG,"loadmore"+berforeDateShow);
        int oldItemNum = latestNewsAdapter.getItemCount();
        LatestNewsItem latestNewsItem0= new LatestNewsItem(berforeDateShow,LatestNewsItem.TYPE_DATE);
        latestNewsItemsList.add(latestNewsItem0);
        for(LatestNews.StoriesBean beforeStoriesBean : beforeStoriesBeanList){
            LatestNewsItem latestNewsItem= new LatestNewsItem(beforeStoriesBean.getTitle(),beforeStoriesBean.getImages().get(0),LatestNewsItem.TYPE_NEW);
            latestNewsItemsList.add(latestNewsItem);
        }
        latestNewsAdapter.notifyItemRangeChanged(oldItemNum,beforeStoriesBeanList.size() + 1);
        beforeDays++;
        isLoadMore = false;

    }

    public void refreshNews(){
        //presenter.getLatestNews();
        if(!DateUtil.isSystemDate(date)){
            presenter.getLatestNews();
        }else{
            //presenter.getLatestNews();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void initDate() {
        isLoadMore = false;
        isRefresh = false;
        beforeDays = 0;
        presenter.getLatestNews();

    }
}
