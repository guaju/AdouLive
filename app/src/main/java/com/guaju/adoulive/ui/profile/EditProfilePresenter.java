package com.guaju.adoulive.ui.profile;

import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.engine.TimProfileHelper;

/**
 * Created by guaju on 2018/1/3.
 */

public class EditProfilePresenter implements EditProfileContract.Presenter {
    EditProfileContract.View view;
    EditProfileActivity editProfileActivity;



    public EditProfilePresenter(EditProfileContract.View view) {
        this.view = view;
        editProfileActivity = (EditProfileActivity) view;
    }

    @Override
    public void getUserInfo() {
        //app拿，如果没有再自己去获取

        TimProfileHelper.getInstance().getSelfProfile(editProfileActivity, new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(AdouTimUserProfile mProfile) {
                view.updateView(mProfile);
            }

            @Override
            public void noGet() {
                view.onGetInfoFailed();
            }
        });


    }

    @Override
    public void onUpdateInfoSuccess() {
        //更新application信息
      TimProfileHelper.getInstance().resetApplicationProfile(new TimProfileHelper.OnProfileGet() {
          @Override
          public void onGet(AdouTimUserProfile mProfile) {
              view.updateView(mProfile);
          }

          @Override
          public void noGet() {

          }
      });
    }
}
