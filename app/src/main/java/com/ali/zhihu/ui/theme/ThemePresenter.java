package com.ali.zhihu.ui.theme;

import com.ali.zhihu.bean.Theme;
import com.ali.zhihu.net.ThemeApi;
import com.ali.zhihu.ui.base.BaseObserver;
import com.ali.zhihu.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ThemePresenter extends BasePresenter<ThemeContract.ThemeView> implements ThemeContract.ThemePresenter {
    private ThemeApi themeApi;
    @Inject
    public ThemePresenter(ThemeApi themeApi){
        this.themeApi = themeApi;
    }

    @Override
    public void getTheme(int themeId) {
        themeApi.getTheme(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Theme>() {
                    @Override
                    public void onSuccsee(Theme theme) {
                        mView.loadThemeView(theme.getStories());
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }

    @Override
    public void getBeforeTheme(int themeId, int storyId) {
        themeApi.getBeforeTheme(themeId,storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Theme>() {
                    @Override
                    public void onSuccsee(Theme theme) {
                        mView.loadBeforeThemeView(theme.getStories());
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }

    @Override
    public void getHeader(int themeId) {
        themeApi.getTheme(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Theme>() {
                    @Override
                    public void onSuccsee(Theme theme) {
                        mView.loadThemeHeaderView(theme.getDescription(),theme.getBackground());
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }
}
