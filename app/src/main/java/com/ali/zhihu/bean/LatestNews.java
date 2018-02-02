package com.ali.zhihu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 *
 *
 * 格式
 *  {
        date: "20140523",
        stories: [
            {
                title: "中国古代家具发展到今天有两个高峰，一个两宋一个明末（多图）",
                ga_prefix: "052321",
                images: [
                    "http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"
                ],
                type: 0,
                id: 3930445
            },
        ...
        ],
        top_stories: [
            {
                title: "商场和很多人家里，竹制家具越来越多（多图）",
                image: "http://p2.zhimg.com/9a/15/9a1570bb9e5fa53ae9fb9269a56ee019.jpg",
                ga_prefix: "052315",
                type: 0,
                id: 3930883
            },
        ...
        ]
    }
 ***************注意字段名字必须和GSON一模一样，否则会出差
 */

public class LatestNews implements Serializable {
    private String date;

    public String getDate() {
        return date;
    }

    public List<StoriesBean> stories;

    public List<TopStoriesBean> top_stories;

    @Override
    public String toString() {
        return "{"
                + "date:" + date +","
                + stories
                + top_stories
                + "}";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean implements Serializable{
        private String title;
        //供Goole Analytics使用
        private String ga_prefix;
        private List<String> images;
        //作用未知
        private int type;
        //内容id
        private String id;

        @Override
        public String toString() {
            return "stories:{"
                    + "title:" + title +","
                    + "ga_prefix" + ga_prefix +","
                    + "images: [" + images +"],"
                    + "type:" + type +","
                    + "id:" + id +","
                    + "}";
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
    public static class TopStoriesBean implements Serializable{
        private String title;
        //供Goole Analytics使用
        private String ga_prefix;
        private String image;
        //作用未知
        private int type;
        //内容id
        private String id;

        @Override
        public String toString() {
            return "top_stories:{"
                    + "title:" + title +","
                    + "ga_prefix" + ga_prefix +","
                    + "image: [" + image +"],"
                    + "type:" + type +","
                    + "id:" + id +","
                    + "}";
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
