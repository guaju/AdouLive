package com.guaju.adoulive.engine.live;

import android.content.Context;

import com.guaju.adoulive.utils.ToastUtils;
import com.tencent.av.sdk.AVRoomMulti;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.ilivesdk.core.ILiveRoomManager;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by guaju on 2018/1/8.
 */

public class LiveHelper {
    Context mContext;
    static LiveHelper mLiveHelper;
    OkHttpClient mOkHttpClient;


    private LiveHelper(Context context) {
        this.mContext = context;
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public static LiveHelper getInstance(Context context) {
        if (mLiveHelper == null) {
            mLiveHelper = new LiveHelper(context);
        }
        return mLiveHelper;
    }


    // 创建房间
    public  void createRoom(String roomNume) {
        int roomId = DemoFunc.getIntValue(roomNume, -1);
        if (-1 == roomId) {
            ToastUtils.show("房间号不合法");
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption(ILiveLoginManager.getInstance().getMyUserId())
//                .setRoomMemberStatusLisenter(this)
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .controlRole(Constants.ROLE_MASTER)
                .authBits(AVRoomMulti.AUTH_BITS_DEFAULT)//权限设置
                .videoRecvMode(AVRoomMulti.VIDEO_RECV_MODE_SEMI_AUTO_RECV_CAMERA_VIDEO)
                .autoFocus(true);
        UserInfo.getInstance().setRoom(roomId);
        ILVLiveManager.getInstance().createRoom(UserInfo.getInstance().getRoom(),
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        afterCreate();
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        if (module.equals(ILiveConstants.Module_IMSDK) && 10021 == errCode) {
                            // 被占用，改加入
//                            showChoiceDlg();
                        } else {
                            ToastUtils.show("创建房间失败");
                        }
                    }
                });
    }

    public void afterCreate() {
        UserInfo.getInstance().setRoom(ILiveRoomManager.getInstance().getRoomId());
        UserInfo.getInstance().writeToCache(mContext);
//        etRoom.setEnabled(false);
//        findViewById(R.id.tv_create).setVisibility(View.INVISIBLE);
//        cancelMixStream(false, 0);
        //addMessage("Mix", "View : "+txvvPlayerView.getWidth()+"*"+txvvPlayerView.getHeight());
    }


}
