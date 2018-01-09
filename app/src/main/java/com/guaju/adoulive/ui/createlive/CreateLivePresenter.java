package com.guaju.adoulive.ui.createlive;

import android.content.Intent;
import android.text.TextUtils;

import com.guaju.adoulive.ui.host.HostLiveActivity;

/**
 * Created by guaju on 2018/1/8.
 */

public class CreateLivePresenter implements CreateLiveContract.Presenter {
    CreateLiveContract.View view;
    CreateLiveActivity mActivity;

    @Override
    public void createLive(String url, String liveName) {
      //创建直播
      if (TextUtils.isEmpty(url)|| TextUtils.isEmpty(liveName)){
          view.onCreateFailed();
      }else {
          //尝试去创建  找腾讯云代码
          Intent intent = new Intent(mActivity, HostLiveActivity.class);
          mActivity.startActivity(intent);

      }



    }

    public CreateLivePresenter(CreateLiveContract.View view) {
        this.view = view;
        mActivity=(CreateLiveActivity)view;

    }
}
