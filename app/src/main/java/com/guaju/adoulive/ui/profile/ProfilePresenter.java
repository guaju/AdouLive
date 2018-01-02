package com.guaju.adoulive.ui.profile;

import com.guaju.adoulive.app.AdouApplication;
import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.engine.TimProfileHelper;

/**
 * Created by guaju on 2018/1/2.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    ProfileActivity activity;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        activity=(ProfileActivity)view;
    }

    @Override
    public void getUserProfile() {
        AdouTimUserProfile adouTimUserProfile = AdouApplication.getApp().getAdouTimUserProfile();
        if (adouTimUserProfile!=null){
        view.updateProfile(adouTimUserProfile);
        }else{
            new TimProfileHelper().getSelfProfile(activity, new TimProfileHelper.OnProfileGet() {
                @Override
                public void onGet(AdouTimUserProfile mProfile) {
                    view.updateProfile(mProfile);
                }

                @Override
                public void noGet() {
                     //没有获取到
                   view.updateProfileError();
                }
            });
        }
    }
}
