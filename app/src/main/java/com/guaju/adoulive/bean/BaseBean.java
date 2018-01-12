package com.guaju.adoulive.bean;

/**
 * Created by guaju on 2018/1/12.
 */

public class BaseBean<T> {


    /**
     * code : 1
     * errCode :
     * errMsg :
     * data : [{"roomId":4,"userId":"adsfa","userName":"afds","userAvatar":"afdsds","liveCover":"adsf","liveTitle":"adsf","watcherNums":0},{"roomId":16,"userId":"xiaoming","userName":"à¼ºä¹±è\u0088\u009eæ\u0098¥ç§\u008bà¼»","userAvatar":"http://oucijhbtq.bkt.clouddn.com/xiaoming1515298392502_crop.jpg","liveCover":"http://oucijhbtq.bkt.clouddn.com/xiaoming1515651194433_crop.jpg","liveTitle":"123","watcherNums":0},{"roomId":23,"userId":"jinruisr","userName":"å\u0093\u0088å\u0093\u0088","userAvatar":"http://oys0wzcwl.bkt.clouddn.com/1515498650860_crop.jpg","liveCover":"http://oys0wzcwl.bkt.clouddn.com/jinruisr1515661492364_crop.jpg","liveTitle":"å\u0093\u0088å\u0093\u0088","watcherNums":0}]
     */

    private String code;
    private String errCode;
    private String errMsg;
    private T t;

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

    public T getData() {
        return t;
    }

    public void setData(T data) {
        this.t = data;
    }
}
