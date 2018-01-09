package com.guaju.adoulive.ui.host;

import com.guaju.adoulive.engine.live.LiveHelper;

/**
 * Created by guaju on 2018/1/8.
 */

public class HostPresenter implements HostLiveContract.Presenter {
    HostLiveContract.View mView;
    HostLiveActivity hostLiveActivity;
    @Override
    public void createHost() {
        LiveHelper.getInstance(hostLiveActivity).createRoom("00000001");
    }

    public HostPresenter(HostLiveContract.View mView) {
        this.mView = mView;
        hostLiveActivity=(HostLiveActivity)mView;
    }
}
