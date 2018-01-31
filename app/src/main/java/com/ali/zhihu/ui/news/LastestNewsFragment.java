package com.ali.zhihu.ui.news;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.zhihu.MyApplication;
import com.ali.zhihu.R;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.component.ApplicationComponent;
import com.ali.zhihu.component.DaggerHttpComponent;
import com.ali.zhihu.module.HttpModule;
import com.ali.zhihu.ui.adapter.LatestNewsAdapter;
import com.ali.zhihu.ui.base.BaseFragment;
import com.ali.zhihu.ui.base.BasePresenter;
import com.ali.zhihu.ui.news.contract.LatestNewsContract;
import com.ali.zhihu.ui.news.presenter.LatestNewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class LastestNewsFragment extends BaseFragment<LatestNewsPresenter> implements LatestNewsContract.LastestNewView{
    private LatestNewsAdapter latestNewsAdapter;
    private RecyclerView latestNewsRecyclerView;
    private TextView textView;
    private List<LatestNews.StoriesBean> latestNewsStoriesBeanList;
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
        latestNewsStoriesBeanList = new ArrayList<>();
        textView = (TextView) getActivity().findViewById(R.id.get_news);
        latestNewsRecyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view_latest_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        latestNewsRecyclerView.setLayoutManager(layoutManager);
        latestNewsAdapter = new LatestNewsAdapter(latestNewsStoriesBeanList);
        latestNewsRecyclerView.setAdapter(latestNewsAdapter);
    }

    @Override
    public void loadView(List<LatestNews.StoriesBean> storiesBeanList) {
        int oldItemNum = latestNewsAdapter.getItemCount();
        latestNewsStoriesBeanList.clear();
        int i = 0;
        for(LatestNews.StoriesBean storiesBean : storiesBeanList){
            latestNewsStoriesBeanList.add(i,storiesBean);
            i++;
        }
        if(oldItemNum == 0){
            latestNewsAdapter.notifyDataSetChanged();
        }else{
            latestNewsAdapter.notifyItemRangeInserted(0,storiesBeanList.size() - 1);
            latestNewsAdapter.notifyItemRangeChanged(storiesBeanList.size(),latestNewsAdapter.getItemCount());
        }
    }

    @Override
    public void initDate() {
        presenter.getLatestNews();
    }
}
