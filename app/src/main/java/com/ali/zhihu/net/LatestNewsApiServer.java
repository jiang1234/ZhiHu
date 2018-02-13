package com.ali.zhihu.net;

import com.ali.zhihu.bean.Article;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.bean.LongComment;
import com.ali.zhihu.bean.ShortComment;

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

    @GET("news/{articleId}")
    Observable<Article> readAticle(@Path("articleId") int articleId);

    @GET("story/{articleId}/long-comments")
    Observable<LongComment> readLongComment(@Path("articleId") String articleId);

    @GET("story/{articleId}/short-comments")
    Observable<ShortComment> readShortComment(@Path("articleId") String articleId);

}
