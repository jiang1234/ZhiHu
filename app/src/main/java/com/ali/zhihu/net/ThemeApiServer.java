package com.ali.zhihu.net;

import com.ali.zhihu.bean.Theme;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/2/10.
 */

public interface ThemeApiServer {
    @GET("theme/{themeId}")
    Observable<Theme> getTheme(@Path("themeId") int themeId);

    @GET("theme/{themeId}/before/{storyId}")
    Observable<Theme> getBeforeTheme(@Path("themeId") int themeId,
                                @Path("storyId") int storyId);
}
