package com.guaju.adoulive.engine;

import android.app.Activity;

import com.guaju.adoulive.app.AdouApplication;
import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

/**
 * Created by guaju on 2018/1/2.
 */

public class TimProfileHelper {
    private static TimProfileHelper helper = new TimProfileHelper();

    public static TimProfileHelper getInstance() {
        return helper;
    }

    public interface OnProfileGet {
        void onGet(AdouTimUserProfile mProfile);
        void noGet();
    }

    //拿到个人信息方法
    public void getSelfProfile(final Activity activity, final OnProfileGet onProfileGet) {
        //先从app中拿
        AdouTimUserProfile adouTimUserProfile = AdouApplication.getApp().getAdouTimUserProfile();
        if (adouTimUserProfile != null) {
            onProfileGet.onGet(adouTimUserProfile);
            return;
        }
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                if (onProfileGet != null)
                    onProfileGet.noGet();

            }

            @Override
            public void onSuccess(TIMUserProfile profile) {
                AdouTimUserProfile mProfile = new AdouTimUserProfile();
                //设置整个profile
                if (profile != null) {
                    mProfile.setProfile(profile);
                }
                //附加信息
//            Map<String, byte[]> customInfo = profile.getCustomInfo();
//            byte[] gradeBytes = customInfo.get(CustomTimProfileInfo.INFO_GRADE);
//            byte[] receiveBytes = customInfo.get(CustomTimProfileInfo.INFO_RECEIVE);
//            byte[] sendBytes = customInfo.get(CustomTimProfileInfo.INFO_SEND);
//            byte[] xingzuoBytes = customInfo.get(CustomTimProfileInfo.INFO_XINGZUO);
//            if (gradeBytes!=null){
//                mProfile.setGrade(Integer.parseInt(new String(gradeBytes)));
//            }
//            if (receiveBytes!=null){
//                mProfile.setReceive(Integer.parseInt(new String(receiveBytes)));
//            }
//            if (sendBytes!=null){
//                mProfile.setSend(Integer.parseInt(new String(sendBytes)));
//            }
//            if (xingzuoBytes!=null){
//                mProfile.setXingzuo(new String(xingzuoBytes));
//            }
                AdouApplication.getApp().setAdouTimUserProfile(mProfile);
                if (onProfileGet != null)
                    onProfileGet.onGet(mProfile);
            }

        });
    }

    public void resetApplicationProfile(final OnProfileGet onProfileGet){
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                 onProfileGet.noGet();
            }
            @Override
            public void onSuccess(TIMUserProfile profile) {
                AdouTimUserProfile mProfile = new AdouTimUserProfile();
                //设置整个profile
                if (profile != null) {
                    mProfile.setProfile(profile);
                    onProfileGet.onGet(mProfile);
                }
                //附加信息
//            Map<String, byte[]> customInfo = profile.getCustomInfo();
//            byte[] gradeBytes = customInfo.get(CustomTimProfileInfo.INFO_GRADE);
//            byte[] receiveBytes = customInfo.get(CustomTimProfileInfo.INFO_RECEIVE);
//            byte[] sendBytes = customInfo.get(CustomTimProfileInfo.INFO_SEND);
//            byte[] xingzuoBytes = customInfo.get(CustomTimProfileInfo.INFO_XINGZUO);
//            if (gradeBytes!=null){
//                mProfile.setGrade(Integer.parseInt(new String(gradeBytes)));
//            }
//            if (receiveBytes!=null){
//                mProfile.setReceive(Integer.parseInt(new String(receiveBytes)));
//            }
//            if (sendBytes!=null){
//                mProfile.setSend(Integer.parseInt(new String(sendBytes)));
//            }
//            if (xingzuoBytes!=null){
//                mProfile.setXingzuo(new String(xingzuoBytes));
//            }
                AdouApplication.getApp().setAdouTimUserProfile(mProfile);
            }

        });
    }
}
