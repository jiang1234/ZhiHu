package com.ali.zhihu.ui.theme;

import com.ali.zhihu.bean.Theme;
import com.ali.zhihu.net.ThemeApi;
import com.ali.zhihu.ui.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2018/2/10.
 */

public interface ThemeContract {
    interface ThemePresenter extends BaseContract.BasePresenter<ThemeView>{
        void getTheme(int themeId);
        void getBeforeTheme(int themeId,int storyId);
        void getHeader(int themeId);
    }

    interface ThemeView extends BaseContract.BaseView{
        void loadThemeView(List<Theme.Stories> stories);
        void loadBeforeThemeView(List<Theme.Stories> stories);
        void loadThemeHeaderView(String descripton, String backgroud);
    }
}
