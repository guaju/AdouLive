package com.guaju.adoulive.ui.login;

import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by guaju on 2018/1/1.
 */

public class LoginPresenter implements LoginContract.Presenter{
    //持有view的引用
    LoginContract.View mView;
    LoginActivity loginActivity;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        loginActivity=(LoginActivity)mView;
    }
    //登录
    @Override
    public void login(String acount, String pass) {
        //为空判断
        if (TextUtils.isEmpty(acount)||TextUtils.isEmpty(pass)){
              mView.loginInfoEmpty();
              return;
        }
        if (acount.length()<8||pass.length()<8){
              mView.loginInfoError();
              return;
        }
        realLogin(acount,pass);

    }

    private void realLogin(String acount, String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mView.loginSuccess();

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
              mView.loginFailed();
            }
        });
    }
}
