package com.guaju.adoulive.bean;

/**
 * Created by guaju on 2018/1/16.
 * 弹幕bean
 */

public class DanmuMsgInfo {

    String avatar;
    int grade;
    String nickname;
    String text;
    String adouID;

    public DanmuMsgInfo() {
    }

    public String getAvatar() {
        return avatar;
    }

    public DanmuMsgInfo(String avatar, int grade, String nickname, String text, String adouID) {
        this.avatar = avatar;
        this.grade = grade;
        this.nickname = nickname;
        this.text = text;
        this.adouID = adouID;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdouID() {
        return adouID;
    }

    public void setAdouID(String adouID) {
        this.adouID = adouID;
    }


}

