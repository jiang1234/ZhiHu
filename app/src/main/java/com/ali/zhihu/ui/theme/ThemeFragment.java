package com.ali.zhihu.ui.theme;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.zhihu.MyApplication;
import com.ali.zhihu.R;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.bean.LatestNewsItem;
import com.ali.zhihu.bean.Theme;
import com.ali.zhihu.bean.ThemeItem;
import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerHttpComponent;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.adapter.ThemeAdapter;
import com.ali.zhihu.ui.base.BaseFragment;
import com.ali.zhihu.ui.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ThemeFragment extends BaseFragment<ThemePresenter> implements ThemeContract.ThemeView {
    public static final String THEME_ID = "THEME_ID";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<ThemeItem> themeItemList;
    private View headerView;
    private ThemeAdapter themeAdapter;
    private int themeId;
    private ImageView themeHeader;
    private TextView themeHeaderView;
    private int storyId;
    private boolean isLoadMore;
    private boolean isRefresh;
    private int endRefreshIndex;
    @Override
    public int getLayout() {
        return R.layout.fragment_theme;
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
        themeItemList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view_theme);
        headerView = getView().inflate(getActivity(),R.layout.theme_header,null);
        themeHeader = (ImageView)headerView.findViewById(R.id.theme_header);
        themeHeaderView = (TextView)headerView.findViewById(R.id.theme_header_text);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        themeAdapter = new ThemeAdapter(themeItemList,getActivity());
        recyclerView.setAdapter(themeAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!isLoadMore && !recyclerView.canScrollVertically(1)){
                    isLoadMore = true;
                    presenter.getBeforeTheme(themeId,storyId);
                }
            }
        });
        headerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.getHeight()/3));

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    isRefresh = true;
                    refreshTheme();
                }
            }
        });


    }

    @Override
    public void initDate() {
        isLoadMore = false;
        Bundle data = getArguments();
        themeId = data.getInt(THEME_ID);
        presenter.getHeader(themeId);
        presenter.getTheme(themeId);
    }

    @Override
    public void loadThemeView(List<Theme.Stories> storiesList) {
        if(themeItemList.size() != 0){
             endRefreshIndex = needRefresh(themeItemList.get(0).getId(),storiesList);
            if(endRefreshIndex == -1){
                isRefresh = false;
                swipeRefreshLayout.setRefreshing(false);
                return;
            }else{
                storyId = themeAdapter.refreshTheme(storiesList,endRefreshIndex);
                isRefresh = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        storyId = themeAdapter.addTheme(storiesList);
        isRefresh = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadBeforeThemeView(List<Theme.Stories> storiesList) {
        storyId = themeAdapter.addBeforeTheme(storiesList);
        isLoadMore = false;
    }

    @Override
    public void loadThemeHeaderView(String descripton, String background) {
        ThemeItem themeItem = new ThemeItem(background,descripton);
        if(background != null){
            ImageLoaderUtil.GlideImageLoader(getContext(),background,themeHeader);
        }

        themeHeaderView.setText(descripton);
        themeAdapter.setHeaderView(headerView);
    }

    public void refreshTheme(){
        presenter.getTheme(themeId);
    }
    public int needRefresh(int storyId,List<Theme.Stories> storiesList){
        //return storyId == storiesList.get(0).getId();

        for(int i = 0; i < storiesList.size(); i++ ){
            if(storyId == storiesList.get(i).getId()){
                return i - 1;
            }
        }
        return storiesList.size() - 1;
    }

}
