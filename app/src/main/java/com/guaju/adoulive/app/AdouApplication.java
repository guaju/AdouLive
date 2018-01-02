package com.guaju.adoulive.app;

import android.app.Application;

import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.engine.MessageObservable;
import com.guaju.adoulive.timcustom.CustomTimProfileInfo;
import com.tencent.TIMManager;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guaju on 2018/1/1.
 */

public class AdouApplication extends Application {
   static  AdouApplication app;
   AdouTimUserProfile adouTimUserProfile;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initLiveSdk();


    }

    private void initLiveSdk() {
        if(MsfSdkUtils.isMainProcess(this)){    // 仅在主线程初始化
            // 初始化LiveSDK
            ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
            ILiveSDK.getInstance().initSdk(this, 1400059239, 21019);
            ILVLiveManager.getInstance().init(new ILVLiveConfig()
                    .setLiveMsgListener(MessageObservable.getInstance()));
            long type=CustomTimProfileInfo.ALL_BASE_INFO;
            List<String> customFields=new ArrayList<>();
//            customFields.add(CustomTimProfileInfo.INFO_FANS);
//            customFields.add(CustomTimProfileInfo.INFO_FORK);
            customFields.add(CustomTimProfileInfo.INFO_GRADE);
            customFields.add(CustomTimProfileInfo.INFO_RECEIVE);
            customFields.add(CustomTimProfileInfo.INFO_SEND);
//            customFields.add(CustomTimProfileInfo.INFO_XINGZUO);
            TIMManager.getInstance().initFriendshipSettings(type, customFields);
        }
    }

    public  static AdouApplication getApp(){
        return app;
    }

    public void setAdouTimUserProfile(AdouTimUserProfile profile){
        if (profile!=null){
        adouTimUserProfile=profile;
        }
    }
    public AdouTimUserProfile getAdouTimUserProfile(){
        return  adouTimUserProfile;
    }


}
