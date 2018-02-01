package com.ali.zhihu.net;

import com.ali.zhihu.bean.LatestNews;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface LatestNewsApiServer {
    @GET("news/latest")
    Observable<LatestNews> getLatestNews();

    @GET("news/before/{dateId}")
    Observable<LatestNews> getBeforeNews(@Path("dateId") String dateId);

}
