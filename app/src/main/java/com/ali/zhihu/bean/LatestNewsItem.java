package com.ali.zhihu.bean;

import com.ali.zhihu.bean.LatestNews;

/**
 * Created by Administrator on 2018/1/31.
 */

public class LatestNewsItem {
    public final static int TYPE_NEW = 0;
    public final static int TYPE_DATE = 1;
    public final static int TYPE_HEADER = 2;
    private String news;
    private String imageId;
    private String date;
    private int id;
    private int type;

    public LatestNewsItem(String news,String imageId,int type,int id){
        this.news = news;
        this.imageId = imageId;
        this.type = type;
        this.id = id;
    }

    public LatestNewsItem(String date,int type){
        this.date = date;
        this.type = type;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
