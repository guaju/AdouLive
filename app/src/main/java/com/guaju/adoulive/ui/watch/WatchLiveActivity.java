package com.guaju.adoulive.ui.watch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.guaju.adoulive.R;
import com.guaju.adoulive.engine.live.Constants;
import com.guaju.adoulive.engine.live.DemoFunc;
import com.guaju.adoulive.utils.ToastUtils;
import com.guaju.adoulive.widget.BottomSwitchLayout;
import com.orhanobut.logger.Logger;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;

/**
 * Created by guaju on 2018/1/12.
 */


public class WatchLiveActivity extends Activity {

    private AVRootView avRootView;
    private int roomId;
    private String hostId;
    private BottomSwitchLayout bottomswitchlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_live);
        initView();
        setBottomSwitchListener();
        initRootView();
        //获取房间号，和主播号
        getinfoAndJoinRoom();

    }

    private void setBottomSwitchListener() {
        bottomswitchlayout.setOnSwitchListener(new BottomSwitchLayout.OnSwitchListener() {
            @Override
            public void onChat() {
                //聊天
                ToastUtils.show("聊天");
            }

            @Override
            public void onClose() {
                //关闭时
                finish();
            }
        });

    }

    private void getinfoAndJoinRoom() {
        Intent intent = getIntent();
        if (intent != null) {
            roomId = intent.getIntExtra("roomId", -1);
            hostId = intent.getStringExtra("hostId");
            joinRoom(roomId + "");
        }
    }

    private void initRootView() {
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
    }

    private void initView() {
        avRootView = findViewById(R.id.av_rootview);
        bottomswitchlayout = findViewById(R.id.bottomswitchlayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
        Logger.e("onpause观看者");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
        Logger.e("onresume观看者");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e("ondestoy观看者");
        quitRoom();
        ILVLiveManager.getInstance().onDestory();
    }

    // 加入房间
    private void joinRoom(String roomString) {
        int roomId = DemoFunc.getIntValue(roomString, -1);
        if (-1 == roomId) {
            ToastUtils.show("房间号不合法");
            //退出房间
            finish();
            return;
        }
        ILVLiveRoomOption option = new ILVLiveRoomOption("")
                .controlRole(Constants.ROLE_GUEST) //是一个浏览者
                .videoMode(ILiveConstants.VIDEOMODE_NORMAL)
                .autoCamera(false)
                .autoMic(false);
        ILVLiveManager.getInstance().joinRoom(roomId,
                option, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        //成功的时候怎么办 (增加房间观看数量)


                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtils.show("加入房间失败，正在退出。。。");
                        //退出房间
                        finish();
                    }
                });

    }

    //退出房间的操作
    public void quitRoom() {
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                //退出成功
                ToastUtils.show("退出房间成功");
                //修改房间的观看数量
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });
    }
}
