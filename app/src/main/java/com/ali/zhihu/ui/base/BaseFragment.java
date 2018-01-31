package com.ali.zhihu.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.zhihu.MyApplication;
import com.ali.zhihu.R;
import com.ali.zhihu.component.ApplicationComponent;

import javax.inject.Inject;

/**
 * Created by Administrator on 2018/1/30.
 */

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends android.support.v4.app.Fragment implements IBase,BaseContract.BaseView{
    @Inject
    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(),container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInjector(MyApplication.getApplicationComponent());
        attachView();
        initView();
        initDate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    @Override
    public void attachView() {
        if(presenter != null){
            presenter.attachView(this);
        }
    }

    @Override
    public void detachView() {
        if(presenter != null){
            presenter.detachView();
        }
    }
}

