package com.ali.zhihu.net;


import com.ali.zhihu.bean.Article;
import com.ali.zhihu.bean.LatestNews;
import com.ali.zhihu.bean.LongComment;
import com.ali.zhihu.bean.ShortComment;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/1/30.
 */

public class LatestNewsApi {
    private LatestNewsApiServer latestNewsApiServer;
    private static LatestNewsApi sInstance;

    //构造函数私有化 单例模式
    private LatestNewsApi(LatestNewsApiServer latestNewsApiServer){
        this.latestNewsApiServer = latestNewsApiServer;
    }

    public static LatestNewsApi getInstance(LatestNewsApiServer latestNewsApiServer){
        if(sInstance == null){
            sInstance = new LatestNewsApi(latestNewsApiServer);
        }
        return sInstance;
    }

    public Observable<LatestNews> getLatestNews(){
        return latestNewsApiServer.getLatestNews();
    }

    public Observable<LatestNews> getBeforeNews(String dateId){
        return latestNewsApiServer.getBeforeNews(dateId);
    }

    public Observable<Article> readArticle(String articleId){
        return latestNewsApiServer.readAticle(articleId);
    }

    public Observable<LongComment> readLongComment(String articleId){
        return latestNewsApiServer.readLongComment(articleId);
    }

    public Observable<ShortComment> readShortComment(String articleId){
        return latestNewsApiServer.readShortComment(articleId);
    }
}
