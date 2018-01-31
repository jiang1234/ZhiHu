package com.ali.zhihu.net;

import com.ali.zhihu.bean.LatestNews;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface LatestNewsApiServer {
    @GET("news/latest")
    Observable<LatestNews> getLatestNews();

}
