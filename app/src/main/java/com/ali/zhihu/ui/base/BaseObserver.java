package com.ali.zhihu.ui.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/31.
 */

public abstract class BaseObserver<T> implements Observer<T>{

    public abstract void onSuccsee(T t);

    public abstract void onFail(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {
        this.onSuccsee(value);
    }

    @Override
    public void onError(Throwable e) {
        this.onFail(e);
    }

    @Override
    public void onComplete() {

    }
}
