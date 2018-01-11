package com.guaju.adoulive.bean;

/**
 * Created by guaju on 2018/1/11.
 */

public class HomeInfo {

    /**
     * roomId : 894
     * userId : xiaoming
     * userName : smartisan
     * userAvatar : http://oucijhbtq.bkt.clouddn.com/龙纹身的女孩.jpg
     * liveCover : http://oucijhbtq.bkt.clouddn.com/龙纹身的女孩.jpg
     * liveTitle : 很黄很暴力
     * watcherNums : 0
     */

    private int roomId;
    private String userId;
    private String userName;
    private String userAvatar;
    private String liveCover;
    private String liveTitle;
    private int watcherNums;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public int getWatcherNums() {
        return watcherNums;
    }

    public void setWatcherNums(int watcherNums) {
        this.watcherNums = watcherNums;
    }
}
