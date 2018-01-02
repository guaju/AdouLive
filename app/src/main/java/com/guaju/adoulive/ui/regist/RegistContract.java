package com.guaju.adoulive.ui.regist;

/**
 * Created by guaju on 2018/1/1.
 */

public interface RegistContract {
    interface View{
        void registSuccess();
        void registError(int errCode, String errMsg);
        //信息为空
        void registInfoEmpty();
        //位数不够
        void registInfoError();
        //两次密码输入不一致
        void registConfirmPassError();
    }
    interface Presenter{
        void regist(String acount,String pass,String confirmPass);
    }

}
