package com.guaju.adoulive.ui.profile;

import com.guaju.adoulive.bean.AdouTimUserProfile;

/**
 * Created by guaju on 2018/1/3.
 */

public interface EditProfileContract {
    interface  View{
       void updateView(AdouTimUserProfile profile);
       void onGetInfoFailed();
       void updateInfoError();
       void updateInfoSuccess();

    }
    interface  Presenter{
        void getUserInfo();
       void onUpdateInfoSuccess();
    }
}
