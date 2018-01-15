package com.guaju.adoulive.bean;

/**
 * Created by guaju on 2018/1/15.
 */

public class TextMsgInfo {
    int grade;
    String nickname;
    String text;
    String adouID;

    public TextMsgInfo() {

    }

    public TextMsgInfo(int grade, String nickname, String text, String adouID) {
        this.grade = grade;
        this.nickname = nickname;
        this.text = text;
        this.adouID = adouID;
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

    @Override
    public String toString() {
        return "TextMsgInfo{" +
                "grade=" + grade +
                ", nickname='" + nickname + '\'' +
                ", text='" + text + '\'' +
                ", adouID='" + adouID + '\'' +
                '}';
    }
}
