package com.ali.zhihu.ui.news;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.zhihu.MyApplication;
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
import com.ali.zhihu.ui.util.ImageLoaderUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//import static com.ali.zhihu.R.id.banner1;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2018/1/30.
 * SwipeRefreshLayout中只能有一个RecyclerView控件 否则会无法更新RecyclerView
 */

public class LastestNewsFragment extends BaseFragment<LatestNewsPresenter> implements LatestNewsContract.LastestNewView{
    public static final String TAG = "LastestNewsFragment";
    private LatestNewsAdapter latestNewsAdapter;
    //private MyAdapter latestNewsAdapter;
    private RecyclerView latestNewsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Banner banner;
    //private Banner banner1;
    private View headerView;
    private List<LatestNewsItem> latestNewsItemsList;
    private List<String> bannerList;
    //private List<String> bannerList1;
    private boolean isLoadMore;
    private boolean isRefresh;
    private String date;
    //用于访问以前的新闻
    private int beforeDays;
    //访问11月20日的 请求为1121
    private String beforeDate;
    //请求为1121 实际为1120
    private String berforeDateShow;
    private int endRefreshIndex;

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getLayout() {
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
        bannerList = new ArrayList<>();
        //bannerList1 = new ArrayList<>();
        //bannerList1.add("https://pic1.zhimg.com//v2-20f16da6af017c3c3201159a90a9e530.jpg");
        //bannerList1.add("https://pic1.zhimg.com//v2-20f16da6af017c3c3201159a90a9e530.jpg");
        swipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh);
        latestNewsRecyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view_latest_news);
        //banner1 = (Banner) getActivity().findViewById(R.id.banner1);
        headerView = getView().inflate(getActivity(),R.layout.latest_header,null);
        banner = (Banner)headerView.findViewById(R.id.banner);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new ImageLoaderUtil.GlideBannerImageLoader());
        banner.setDelayTime(3000);
        banner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.getHeight()/3));

        //banner1.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //banner1.setIndicatorGravity(BannerConfig.CENTER);
        //banner1.setImageLoader(new ImageLoaderUtil.GlideBannerImageLoader());
        //banner1.setDelayTime(1500);
        //banner1.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.getWidth()/4));
        //banner1.setImages(bannerList1);
        //banner1.start();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        latestNewsRecyclerView.setLayoutManager(layoutManager);
        latestNewsAdapter = new LatestNewsAdapter(latestNewsItemsList,getActivity());
        //latestNewsAdapter = new MyAdapter(latestNewsItemsList);

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
        if(latestNewsItemsList.size() != 0){
            endRefreshIndex = needRefresh(latestNewsItemsList.get(1).getId(),storiesBeanList);
            if(endRefreshIndex == -1){
                isRefresh = false;
                swipeRefreshLayout.setRefreshing(false);
                return;
            }else{
                latestNewsAdapter.refreshNew(storiesBeanList,date,endRefreshIndex);
                isRefresh = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        latestNewsAdapter.addNew(storiesBeanList,date);
        isRefresh = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadLatestBanner(final List<LatestNews.TopStoriesBean> topStoriesBeenList) {
        List<String> bannerTitleList = new ArrayList<>();
        bannerList.clear();
        bannerTitleList.clear();
        for(LatestNews.TopStoriesBean topStoriesBean : topStoriesBeenList){
            bannerList.add(topStoriesBean.getImage());
            bannerTitleList.add(topStoriesBean.getTitle());
        }
        banner.setImages(bannerList);
        banner.setBannerTitles(bannerTitleList);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                toReadInBanner(topStoriesBeenList.get(position).getId());
            }
        });
        banner.start();
        latestNewsAdapter.setHeaderView(banner);
    }

    @Override
    public void loadBeforeView(List<LatestNews.StoriesBean> beforeStoriesBeanList) {
        Log.i(TAG,"loadmore"+berforeDateShow);
        int oldItemNum = latestNewsAdapter.getItemCount();
        LatestNewsItem latestNewsItem0= new LatestNewsItem(berforeDateShow,LatestNewsItem.TYPE_DATE);
        latestNewsItemsList.add(latestNewsItem0);
        for(LatestNews.StoriesBean beforeStoriesBean : beforeStoriesBeanList){
            LatestNewsItem latestNewsItem= new LatestNewsItem(beforeStoriesBean.getTitle(),beforeStoriesBean.getImages().get(0),LatestNewsItem.TYPE_NEW,beforeStoriesBean.getId());
            latestNewsItemsList.add(latestNewsItem);
        }
        latestNewsAdapter.notifyItemRangeChanged(oldItemNum,beforeStoriesBeanList.size() + 1);
        beforeDays++;
        isLoadMore = false;

    }



    public void refreshNews(){
        //presenter.getLatestNews();
       // if(!DateUtil.isSystemDate(date)){
            presenter.getLatestNews();
            latestNewsRecyclerView.scrollToPosition(0);
        //}else{
            //presenter.getLatestNews();
        //    isRefresh = false;
        //    swipeRefreshLayout.setRefreshing(false);
        //}
    }
    @Override
    public void initDate() {
        isLoadMore = false;
        isRefresh = false;
        beforeDays = 0;
        presenter.getHeader();
        presenter.getLatestNews();

    }

    public void toReadInBanner(int articleId){
        Intent intent = new Intent(getActivity(), ReadArticleActivity.class);
        intent.putExtra(ReadArticleActivity.ARTICLEID,articleId);
        startActivity(intent);
    }

    public int needRefresh(int articleId,List<LatestNews.StoriesBean> storiesList){
        //return storyId == storiesList.get(0).getId();

        for(int i = 0; i < storiesList.size(); i++ ){
            if(articleId == storiesList.get(i).getId()){
                return i - 1;
            }
        }
        return storiesList.size() - 1;
    }


}
