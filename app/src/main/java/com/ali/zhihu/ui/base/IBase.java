package com.ali.zhihu.ui.base;

import com.ali.zhihu.component.ApplicationComponent;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface IBase {
    int getFragmentLayout();

    void initInjector(ApplicationComponent applicationComponent);

    void attachView();

    void detachView();

    void initView();

    void initDate();

}
