package com.guaju.adoulive.ui.login;

/**
 * Created by guaju on 2018/1/1.
 */

public interface LoginContract {
     interface  View{
        void loginSuccess();
        void loginFailed(int errCode, String errMsg);
        void loginInfoEmpty();
        void loginInfoError();
     }
     interface  Presenter{
        void login(String acount,String pass);
     }


}
