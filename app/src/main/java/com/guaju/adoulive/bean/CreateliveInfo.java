package com.guaju.adoulive.bean;

/**
 * Created by guaju on 2018/1/12.
 */

public class CreateliveInfo {

    /**
     * code : 1
     * errCode :
     * errMsg :
     * data : {"roomId":35,"userId":"xiaoming","userName":"","userAvatar":"http://oucijhbtq.bkt.clouddn.com/龙纹身的女孩.jpg","liveCover":"","liveTitle":"","watcherNums":0}
     */

    private String code;
    private String errCode;
    private String errMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * roomId : 35
         * userId : xiaoming
         * userName :
         * userAvatar : http://oucijhbtq.bkt.clouddn.com/龙纹身的女孩.jpg
         * liveCover :
         * liveTitle :
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
