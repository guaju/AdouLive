package com.guaju.adoulive.bean;

import java.util.ArrayList;

/**
 * Created by guaju on 2018/1/12.
 */

public class AllHomeInfo {

    /**
     * code : 1
     * errCode :
     * errMsg :
     * data : [{"roomId":4,"userId":"adsfa","userName":"afds","userAvatar":"afdsds","liveCover":"adsf","liveTitle":"adsf","watcherNums":0},{"roomId":16,"userId":"xiaoming","userName":"à¼ºä¹±è\u0088\u009eæ\u0098¥ç§\u008bà¼»","userAvatar":"http://oucijhbtq.bkt.clouddn.com/xiaoming1515298392502_crop.jpg","liveCover":"http://oucijhbtq.bkt.clouddn.com/xiaoming1515651194433_crop.jpg","liveTitle":"123","watcherNums":0},{"roomId":23,"userId":"jinruisr","userName":"å\u0093\u0088å\u0093\u0088","userAvatar":"http://oys0wzcwl.bkt.clouddn.com/1515498650860_crop.jpg","liveCover":"http://oys0wzcwl.bkt.clouddn.com/jinruisr1515661492364_crop.jpg","liveTitle":"å\u0093\u0088å\u0093\u0088","watcherNums":0},{"roomId":26,"userId":"CocoCoco","userName":"ç\u0088µè¿¹é£\u008eæ´¥é\u0081\u0093","userAvatar":"http://oys2vnxxz.bkt.clouddn.com/CocoCoco1515721755949_crop.jpg","liveCover":"http://oys2vnxxz.bkt.clouddn.com/CocoCoco1515723493882_crop.jpg","liveTitle":"å\u0095¦å\u0095¦å\u0095¦","watcherNums":0}]
     */

    private String code;
    private String errCode;
    private String errMsg;
    private ArrayList<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * roomId : 4
         * userId : adsfa
         * userName : afds
         * userAvatar : afdsds
         * liveCover : adsf
         * liveTitle : adsf
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
}
