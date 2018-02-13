package com.ali.zhihu.net;

import com.ali.zhihu.bean.Theme;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ThemeApi {
    private ThemeApiServer themeApiServer;
    public static ThemeApi sInstance;
    private ThemeApi(ThemeApiServer themeApiServer){
        this.themeApiServer = themeApiServer;
    }

    public static ThemeApi getInstance(ThemeApiServer themeApiServer){
        if(sInstance == null){
            sInstance = new ThemeApi(themeApiServer);
        }
        return sInstance;
    }

    public Observable<Theme> getTheme(int themeId){
        return themeApiServer.getTheme(themeId);
    }

    public Observable<Theme> getBeforeTheme(int themeId,int storyId){
        return themeApiServer.getBeforeTheme(themeId,storyId);
    }
}
