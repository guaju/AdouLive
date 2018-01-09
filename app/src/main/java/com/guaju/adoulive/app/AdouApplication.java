package com.guaju.adoulive.app;

import android.app.Application;

import com.guaju.adoulive.bean.AdouTimUserProfile;
import com.guaju.adoulive.engine.MessageObservable;
import com.guaju.adoulive.qiniu.QiniuUploadHelper;
import com.guaju.adoulive.timcustom.CustomTimProfileInfo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
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
        //初始化七牛sdk
        initQiniuSdk();
        //初始化logger工具类
        initLogger();

    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("GUAJU")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void initQiniuSdk() {
        QiniuUploadHelper.init(QiniuConfig.SPACENAME,QiniuConfig.SecretKey,QiniuConfig.AccessKey);
    }

    //初始化直播的sdk
    private void initLiveSdk() {
        if(MsfSdkUtils.isMainProcess(this)){    // 仅在主线程初始化
            // 初始化LiveSDK
            ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
            ILiveSDK.getInstance().initSdk(this, 1400059239, 21019);
            ILVLiveManager.getInstance().init(new ILVLiveConfig()
                    .setLiveMsgListener(MessageObservable.getInstance()));

            //初始化自定义资料信息
            long type=CustomTimProfileInfo.ALL_BASE_INFO;
            List<String> customFields=new ArrayList<>();
//            customFields.add(CustomTimProfileInfo.INFO_FANS);
//            customFields.add(CustomTimProfileInfo.INFO_FORK);
            customFields.add(CustomTimProfileInfo.INFO_GRADE);
            customFields.add(CustomTimProfileInfo.INFO_RECEIVE);
            customFields.add(CustomTimProfileInfo.INFO_SEND);
            customFields.add(CustomTimProfileInfo.INFO_XINGZUO);
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
