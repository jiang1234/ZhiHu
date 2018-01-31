package com.ali.zhihu.bean;

import com.ali.zhihu.bean.LatestNews;

/**
 * Created by Administrator on 2018/1/31.
 */

public class LatestNewsItem {
    private String news;
    private int imageId;
    public LatestNewsItem(String news,int imageId){
        this.news = news;
        this.imageId = imageId;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
