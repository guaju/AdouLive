package com.guaju.adoulive.ui.host;

import com.guaju.adoulive.app.AdouApplication;
import com.guaju.adoulive.engine.live.LiveHelper;
import com.guaju.adoulive.httputil.BaseOnRequestComplete;
import com.guaju.adoulive.httputil.Constants;
import com.guaju.adoulive.httputil.OkHttpHelper;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

/**
 * Created by guaju on 2018/1/8.
 */

public class HostPresenter implements HostLiveContract.Presenter {
    HostLiveContract.View mView;
    HostLiveActivity hostLiveActivity;
    @Override
    public void createHost(int roomId) {
        LiveHelper.getInstance(hostLiveActivity).createRoom(roomId+"");

    }

    @Override
    public void quitHost(int roomId) {


        //获取userId 和房间id，然后退出
        TIMUserProfile profile = AdouApplication.getApp().getAdouTimUserProfile().getProfile();
        String userId = profile.getIdentifier();
        HashMap<String, String> map = new HashMap<>();
        map.put("action","quit");
        map.put("userId",userId);
        map.put("roomId",roomId+"");

        OkHttpHelper.getInstance().postString(Constants.HOST, map, new BaseOnRequestComplete<String>() {
            @Override
            public void onSuccess(String s) {
                hostLiveActivity.finish();
            }
        });




    }

    public HostPresenter(HostLiveContract.View mView) {
        this.mView = mView;
        hostLiveActivity=(HostLiveActivity)mView;
    }
}
