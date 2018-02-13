package com.ali.zhihu.bean;

/**
 * Created by Administrator on 2018/2/10.
 */

public class ThemeItem {
    private String background;
    private String description;
    private String images;
    private String title;
    private int id;

    public ThemeItem(String images, String title, int id) {
        this.images = images;
        this.title = title;
        this.id = id;
    }

    public ThemeItem(String background, String description) {
        this.background = background;
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
