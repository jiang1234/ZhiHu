package com.ali.zhihu.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ali.zhihu.MyApplication;


import javax.inject.Inject;

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements IBase,BaseContract.BaseView {
    @Inject
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initInjector(MyApplication.getApplicationComponent());
        attachView();
        initView();
        initDate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.detachView();
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
