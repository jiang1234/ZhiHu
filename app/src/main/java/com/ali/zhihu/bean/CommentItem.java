package com.ali.zhihu.bean;

/**
 * Created by Administrator on 2018/2/4.
 */

public class CommentItem {
    public static final int LONG_COMMENT = 0;
    public static final int SHORT_COMMENT = 1;
    public static final int TYPE_LONG = 2;
    public static final int TYPE_SHORT = 3;
    private String userId;
    private String userImage;
    private String comment;
    private String time;
    private int longCommentNum;
    private int shortCommentNum;
    private int type;
    private boolean collapsed = true;
    private String replyToId;
    private String replyToComment;
    private int replyToStatus;

    public CommentItem(String userId, String userImage, String comment, String time, int type) {
        this.userId = userId;
        this.userImage = userImage;
        this.comment = comment;
        this.time = time;
        this.type = type;
    }

    public CommentItem(int type) {
        this.type = type;
    }

    public CommentItem(int longCommentNum, int type) {
        this.longCommentNum = longCommentNum;
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

    public int getLongCommentNum() {
        return longCommentNum;
    }

    public void setLongCommentNum(int longCommentNum) {
        this.longCommentNum = longCommentNum;
    }

    public int getShortCommentNum() {
        return shortCommentNum;
    }

    public void setShortCommentNum(int shortCommentNum) {
        this.shortCommentNum = shortCommentNum;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(String replyToId) {
        this.replyToId = replyToId;
    }

    public String getReplyToComment() {
        return replyToComment;
    }

    public void setReplyToComment(String replyToComment) {
        this.replyToComment = replyToComment;
    }

    public int getReplyToStatus() {
        return replyToStatus;
    }

    public void setReplyToStatus(int replyToStatus) {
        this.replyToStatus = replyToStatus;
    }
}
