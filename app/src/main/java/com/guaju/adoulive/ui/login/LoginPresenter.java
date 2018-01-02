package com.guaju.adoulive.ui.login;

import android.content.Intent;
import android.text.TextUtils;

import com.guaju.adoulive.engine.TimProfileHelper;
import com.guaju.adoulive.ui.profile.ProfileActivity;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by guaju on 2018/1/1.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";
    //持有view的引用
    LoginContract.View mView;
    LoginActivity loginActivity;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        loginActivity = (LoginActivity) mView;
    }

    //登录
    @Override
    public void login(String acount, String pass) {
        //为空判断
        if (TextUtils.isEmpty(acount) || TextUtils.isEmpty(pass)) {
            mView.loginInfoEmpty();
            return;
        }
        if (acount.length() < 8 || pass.length() < 8) {
            mView.loginInfoError();
            return;
        }
        realLogin(acount, pass);

    }

    //真正的登录
    private void realLogin(String acount, String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mView.loginSuccess();
                getUserInfo();
                Intent intent = new Intent(loginActivity, ProfileActivity.class);
                loginActivity.startActivity(intent);


            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mView.loginFailed(errCode, errMsg);
            }
        });
    }

    //获取用户的个人信息
    private void getUserInfo() {
        new TimProfileHelper().getSelfProfile(loginActivity,null);
    }

}