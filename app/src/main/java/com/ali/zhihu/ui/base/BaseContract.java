package com.ali.zhihu.ui.base;

import java.lang.ref.Reference;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface BaseContract {
    interface BasePresenter<T extends BaseContract.BaseView>{
        void attachView(T view);
        void detachView();
    }
    interface BaseView{

    }
}
