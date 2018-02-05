package com.ali.zhihu.bean;

/**
 * Created by Administrator on 2018/2/4.
 */

public class CommentItem {
    public static final int LONG_COMMENT = 0;
    public static final int SHORT_COMMENT = 1;
    public static final int TYPE = 2;
    private String userId;
    private String userImage;
    private String comment;
    private String time;
    private int type;

    public CommentItem(String userId, String userImage, String comment, String time, int type) {
        this.userId = userId;
        this.userImage = userImage;
        this.comment = comment;
        this.time = time;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
