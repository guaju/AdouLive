package com.guaju.adoulive.ui.watch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.TextMsgInfo;
import com.guaju.adoulive.engine.MessageObservable;
import com.guaju.adoulive.engine.live.Constants;
import com.guaju.adoulive.engine.live.DemoFunc;
import com.guaju.adoulive.timcustom.CustomTimConstant;
import com.guaju.adoulive.utils.ToastUtils;
import com.guaju.adoulive.widget.BottomChatSwitchLayout;
import com.guaju.adoulive.widget.BottomSwitchLayout;
import com.guaju.adoulive.widget.LiveMsgListView;
import com.orhanobut.logger.Logger;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVLiveRoomOption;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guaju on 2018/1/12.
 */


public class WatchLiveActivity extends Activity implements ILVLiveConfig.ILVLiveMsgListener {

    private AVRootView avRootView;
    private int roomId;
    private String hostId;
    private BottomSwitchLayout bottomswitchlayout;


    private LiveMsgListView lmlv;
    //创建集合专门存储消息
    private ArrayList<TextMsgInfo> mList=new ArrayList<TextMsgInfo>();
    private BottomChatSwitchLayout chatswitchlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_live);

        //初始化消息的接受者
        MessageObservable.getInstance().addObserver(this);
        initView();
        lmlv.setData(mList);
        setListener();
        initRootView();
        //获取房间号，和主播号
        getinfoAndJoinRoom();

    }

    private void setListener() {
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
        chatswitchlayout.setOnMsgSendListener(new BottomChatSwitchLayout.OnMsgSendListener() {

            @Override
            public void sendMsg(String text) {
                //发送消息
                sendTextMsg(text,hostId);
            }

            @Override
            public void danmu(String text) {

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
        chatswitchlayout = findViewById(R.id.chatswitchlayout);
        lmlv = findViewById(R.id.lmlv);
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





        //腾讯云发送普通消息
    public void sendTextMsg(final String text, String destId){
        //通过对方id获取对方的等级和对方的昵称

        List<String> ids = new ArrayList<>();
        ids.add(destId);
        TIMFriendshipManager.getInstance().getFriendsProfile(ids, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                realSend(timUserProfiles,text);
            }
        });






    }
    //真正的发送消息
    private void realSend(List<TIMUserProfile> timUserProfiles, final String text) {
        //因为获取信息的时候 只传入了只有一个元素的集合，所以到这只能拿到一个用户的信息
        final TIMUserProfile profile = timUserProfiles.get(0);

        //发送普通消息
        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eGroupMsg, profile.getIdentifier(), text), new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                String grade;
                //发送成功之后，加入到listview中去
                TextMsgInfo textMsgInfo = new TextMsgInfo();
                byte[] bytes = profile.getCustomInfo().get(CustomTimConstant.INFO_GRADE);
                if (bytes!=null){
                    grade = new String(bytes);
                }else{
                    grade="0";
                }
                textMsgInfo.setGrade(Integer.parseInt(grade));
                textMsgInfo.setText(text);
                textMsgInfo.setNickname(profile.getNickName());
                //更新列表
                lmlv.addMsg(textMsgInfo);


            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("发送失败，错误信息"+errMsg+"错误码"+errCode);
            }
        });
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        //当接受到普通消息的时候，展示到listview上边去
        String msg=text.getText();
        String nickName = userProfile.getNickName();
        String grade;
        byte[] bytes = userProfile.getCustomInfo().get(CustomTimConstant.INFO_GRADE);
        if (bytes!=null){
            grade = new String(bytes);
        }else{
            grade="0";
        }
        TextMsgInfo textMsgInfo = new TextMsgInfo(Integer.parseInt(grade), nickName, msg, SenderId);
        lmlv.addMsg(textMsgInfo);

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }

}
