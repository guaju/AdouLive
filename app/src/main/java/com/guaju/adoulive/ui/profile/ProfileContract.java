package com.guaju.adoulive.ui.profile;

import com.guaju.adoulive.bean.AdouTimUserProfile;

/**
 * Created by guaju on 2018/1/2.
 */

public interface ProfileContract {
    interface View{
        void updateProfile(AdouTimUserProfile profile);
        void updateProfileError();
    }
    interface  Presenter{
        void getUserProfile();
    }

}
