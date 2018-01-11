package com.guaju.adoulive.ui.createlive;

import android.content.Intent;
import android.text.TextUtils;

import com.guaju.adoulive.app.AdouApplication;
import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.bean.HostRoomInfo;
import com.guaju.adoulive.httputil.BaseOnRequestComplete;
import com.guaju.adoulive.httputil.Constants;
import com.guaju.adoulive.httputil.OkHttpHelper;
import com.guaju.adoulive.ui.host.HostLiveActivity;
import com.tencent.TIMUserProfile;

import java.util.HashMap;

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
          //尝试去创建
          /*
             先把封面和房间名称 包括创建者id，创建者昵称，创建者头像 (application有缓存)  传给服务器，返回信息中包含roomid
           */
          requestRoomId(url,liveName);
      }



    }
    //获取房间id
    private void requestRoomId(String cover,String liveName) {

        HashMap<String, String> map = new HashMap<>();
        AdouTimUserProfile profile = AdouApplication.getApp().getAdouTimUserProfile();
        TIMUserProfile profile1 = profile.getProfile();
//        String nickname = null;
//        String liveName1=null;
//        try {
//            nickname = URLEncoder.encode(profile1.getNickName(),"UTF-8");
//             liveName1 = URLEncoder.encode(liveName,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        map.put("action","create");     //动作
        map.put("userId",profile1.getIdentifier());  //主播id
        map.put("userAvatar",profile1.getFaceUrl()); //主播头像
        map.put("userName",profile1.getNickName()); //主播昵称
        map.put("liveTitle",liveName); //直播标题
        map.put("liveCover",cover); //直播封面
        OkHttpHelper.getInstance().postObject(Constants.HOST, map, new BaseOnRequestComplete<HostRoomInfo>() {
            @Override
            public void onSuccess(HostRoomInfo info) {
                //当创建直播成功后
                HostRoomInfo.DataBean data = info.getData();
                if (data!=null){
                    int roomId = data.getRoomId();
                    if (roomId!=0){
                        Intent intent = new Intent(mActivity, HostLiveActivity.class);
                        mActivity.startActivity(intent);
                    }
                }
                else{
                    //数据为空
                    onEmpty();
                }


            }
        }, HostRoomInfo.class);
    }

    public CreateLivePresenter(CreateLiveContract.View view) {
        this.view = view;
        mActivity=(CreateLiveActivity)view;

    }
}
