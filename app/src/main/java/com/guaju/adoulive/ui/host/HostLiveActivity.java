package com.guaju.adoulive.ui.host;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.app.AdouApplication;
import com.guaju.adoulive.bean.DanmuMsgInfo;
import com.guaju.adoulive.bean.Gift;
import com.guaju.adoulive.bean.GiftMsgInfo;
import com.guaju.adoulive.bean.TextMsgInfo;
import com.guaju.adoulive.engine.MessageObservable;
import com.guaju.adoulive.timcustom.CustomTimConstant;
import com.guaju.adoulive.utils.ToastUtils;
import com.guaju.adoulive.widget.BottomChatSwitchLayout;
import com.guaju.adoulive.widget.BottomSwitchLayout;
import com.guaju.adoulive.widget.HeightSensenableRelativeLayout;
import com.guaju.adoulive.widget.LiveMsgListView;
import com.guaju.adoulive.widget.danmu.DanmuView;
import com.guaju.adoulive.widget.gift.GiftFullScreenView;
import com.guaju.adoulive.widget.gift.GiftItem;
import com.guaju.adoulive.widget.gift.GiftView;
import com.orhanobut.logger.Logger;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.widget.HeartLayout;

/**
 * Created by guaju on 2018/1/8.
 */

public class HostLiveActivity extends Activity implements HostLiveContract.View, ILVLiveConfig.ILVLiveMsgListener {


    //心形定时器
    Timer heartTimer = new Timer();
    //产生心形颜色的随机器
    Random heartColorRandom = new Random();
    private AVRootView avRootView;
    private GiftView giftView;
    private ImageView iv_switch_camera;
    private BottomSwitchLayout bottomswitchlayout;
    private HostPresenter presenter;
    private Toolbar toolbar;
    private int roomId;
    private BottomChatSwitchLayout chatswitchlayout;
    private HeightSensenableRelativeLayout heightscl;
    private String sendserId;//接受到的信息发送者id
    private LiveMsgListView lmlv;
    //创建集合专门存储消息
    private ArrayList<TextMsgInfo> mList = new ArrayList<TextMsgInfo>();
    private DanmuView danmuView;
    private TextMsgInfo textMsgInfo;


    private static final int FIRST_GIFT_SEND_FLAG = -1;
    public static final int REPEAT_GIFT_SEND_FLAG = 1;
    //倒计时时间范围
    private int repeatTimeLimit = 10;
    long firstSendTimeMillion;
    GiftItem availableGiftItem;

    Handler repeatGiftTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FIRST_GIFT_SEND_FLAG:
                    if (repeatTimeLimit > 0) {
                        repeatTimeLimit--;//开始倒数
                        sendEmptyMessageDelayed(FIRST_GIFT_SEND_FLAG, 80);
                    } else {
                        //倒计时已经数完了，可以重新再开始
                        availableGiftItem.setIsRepeat(false);
                        firstSendTimeMillion = 0;
                        repeatTimeLimit = 10;
                    }

                    break;

                case REPEAT_GIFT_SEND_FLAG:
                    //停止第一个事件 的处理
                    if (repeatTimeLimit > 0) {
                        repeatTimeLimit--;//开始倒数
                        sendEmptyMessageDelayed(REPEAT_GIFT_SEND_FLAG, 80);

                        //用户现在可以连发
                    } else {
                        //倒计时已经数完了，可以重新再开始
                        availableGiftItem.setIsRepeat(false);
                        availableGiftItem.repeatSendWithoutAddNum();
                        firstSendTimeMillion = 0;
                        repeatTimeLimit = 10;
                    }


                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }


        }
    };
    private GiftFullScreenView gift_full_screen_view;
    private HeartLayout heartLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_live);
        //1.初始化消息的接受者
        MessageObservable.getInstance().addObserver(this);
        initView();
        lmlv.setData(mList);
        setBottomSwitchListener();
        //设置默认状态
        setDefultStatus();

        initToolbar();
        initListener();
        initPresenter();
        initCreateHost();

        startHeartAnim();


    }

    private void setDefultStatus() {
        chatswitchlayout.setVisibility(View.INVISIBLE);
        bottomswitchlayout.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        //设置父容器的高度变化监听
        heightscl.setOnLayoutHeightChangedListenser(new HeightSensenableRelativeLayout.OnLayoutHeightChangedListenser() {
            @Override
            public void showNormal() {
                setDefultStatus();
            }

            @Override
            public void showChat() {
                chatswitchlayout.setVisibility(View.VISIBLE);
                bottomswitchlayout.setVisibility(View.INVISIBLE);
            }
        });
        //设置底部chat 聊天的监听
        chatswitchlayout.setOnMsgSendListener(new BottomChatSwitchLayout.OnMsgSendListener() {

            @Override
            public void sendMsg(String text) {
                //主播给客户发消息
                if (TextUtils.isEmpty(sendserId)) {
                    sendserId = AdouApplication.getApp().getAdouTimUserProfile().getProfile().getIdentifier();
                }
                //做唯一表示
                //String newText=CustomTimConstant.TYPE_MSG+text;
                sendTextMsg(text, sendserId, CustomTimConstant.TEXT_MSG);
            }

            @Override
            public void danmu(String text) {
                //主播给客户发消息
                if (TextUtils.isEmpty(sendserId)) {
                    sendserId = AdouApplication.getApp().getAdouTimUserProfile().getProfile().getIdentifier();
                }
                String newText = CustomTimConstant.TYPE_DAN + text;
                sendTextMsg(newText, sendserId, CustomTimConstant.DANMU_MSG);
            }
        });
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭
                //退出直播然后关闭
                presenter.quitHost(roomId);

            }
        });

    }


    private void initCreateHost() {
        Intent intent = getIntent();
        if (intent != null) {
            roomId = intent.getIntExtra("roomId", -1);
        }
        presenter.createHost(roomId);
    }

    private void initPresenter() {
        this.presenter = new HostPresenter(this);
    }

    private void initView() {
        heartLayout = findViewById(R.id.heartlayout);
        gift_full_screen_view = findViewById(R.id.gift_full_screen_view);
        gift_full_screen_view.setVisibility(View.INVISIBLE);
        heightscl = findViewById(R.id.heightscl);
        toolbar = findViewById(R.id.toolbar);
        avRootView = findViewById(R.id.arv_root);
        bottomswitchlayout = findViewById(R.id.bottomswitchlayout);
        bottomswitchlayout.iv_switch_gift.setVisibility(View.INVISIBLE);
        chatswitchlayout = findViewById(R.id.chatswitchlayout);
        //初始化listview
        lmlv = findViewById(R.id.lmlv);
        danmuView = findViewById(R.id.danmuview);
        giftView = findViewById(R.id.giftview);
        //将avrootview添加
        ILVLiveManager.getInstance().setAvVideoView(avRootView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILVLiveManager.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILVLiveManager.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出直播
        quitRoom();
        ILVLiveManager.getInstance().onDestory();
    }

    public void quitRoom() {
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                ToastUtils.show("退出直播成功");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("退出直播失败，错误码" + errCode + "错误信息：" + errMsg);
            }
        });
    }

    private void setBottomSwitchListener() {
        bottomswitchlayout.setOnSwitchListener(new BottomSwitchLayout.OnSwitchListener() {
            @Override
            public void onChat() {
                //聊天
                chatswitchlayout.setVisibility(View.VISIBLE);
                bottomswitchlayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onClose() {
                //关闭时
                finish();
            }

            @Override
            public void onGift() {

            }
        });

    }

    //普通消息
    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        //当接受到普通消息的时候，展示到listview上边去
        sendserId = SenderId;
        String msg = text.getText();
        String nickName = userProfile.getNickName();
        String avatar = userProfile.getFaceUrl();
        String grade;
        byte[] bytes = userProfile.getCustomInfo().get(CustomTimConstant.INFO_GRADE);
        if (bytes != null) {
            grade = new String(bytes);
        } else {
            grade = "0";
        }

        //需要判断发送的是否是弹幕
        if (msg.startsWith(CustomTimConstant.TYPE_DAN)) {
            //是弹幕
            String newMsg = msg.substring(CustomTimConstant.TYPE_DAN.length(), msg.length());
            textMsgInfo = new TextMsgInfo(Integer.parseInt(grade), nickName, newMsg, SenderId);
            //发送弹幕
            DanmuMsgInfo danmuMsgInfo = new DanmuMsgInfo();
            danmuMsgInfo.setText(newMsg);
            danmuMsgInfo.setGrade(Integer.parseInt(grade));
            danmuMsgInfo.setAvatar(avatar);
            danmuMsgInfo.setAdouID(SenderId);
            danmuView.addDanmu(danmuMsgInfo);

        } else if (msg.startsWith(CustomTimConstant.TYPE_GIFT_REPEAT)) {
            //让接收到的消息是动画的话
            availableGiftItem = giftView.getAvailableGiftItem();
            sendGift();
            //设置礼物的信息
            GiftMsgInfo giftMsgInfo = new GiftMsgInfo();
            giftMsgInfo.setAvatar(avatar);
            giftMsgInfo.setAdouID(SenderId);
            //通过消息本体，拿到到底是哪个礼物
            String newMsg = msg.substring(CustomTimConstant.TYPE_GIFT_REPEAT.length(), msg.length());
            //获取消息中的礼物信息
            String giftname = newMsg.replace("送了一个", "");
            Gift gift = Gift.getGiftByName(giftname);

            giftMsgInfo.setGift(gift);


            Logger.e(giftname + "--" + gift);
            //给连发礼物消息绑定数据
            availableGiftItem.bindData(giftMsgInfo);

            textMsgInfo = new TextMsgInfo(Integer.parseInt(grade), nickName, newMsg, SenderId);

        } else if (msg.startsWith(CustomTimConstant.TYPE_GIFT_FULL)) {
            //接受到了全屏礼物
            GiftMsgInfo giftMsgInfo = new GiftMsgInfo();
            giftMsgInfo.setAvatar(avatar);
            giftMsgInfo.setAdouID(SenderId);
            gift_full_screen_view.showFullScreenGift(giftMsgInfo);
            textMsgInfo = new TextMsgInfo(Integer.parseInt(grade), nickName, "送出了一辆保时捷超跑", SenderId);
        } else if (msg.startsWith(CustomTimConstant.TYPE_Heart)) {
            msg = "点亮了一颗心";
            textMsgInfo = new TextMsgInfo(Integer.parseInt(grade), nickName, msg, SenderId);
            addHeart();
        } else {
            textMsgInfo = new TextMsgInfo(Integer.parseInt(grade), nickName, msg, SenderId);
        }
        lmlv.addMsg(textMsgInfo);


    }


    //是自定义消息（可以用作弹幕）
    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    //其他类型的消息
    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }

    //腾讯云发送普通消息
    public void sendTextMsg(final String text, String destId, final int cmd) {
        //通过对方id获取对方的等级和对方的昵称

        List<String> ids = new ArrayList<>();
        ids.add(destId);
        //通过对方的id拿到朋友的信息
        TIMFriendshipManager.getInstance().getFriendsProfile(ids, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {

                realSend(timUserProfiles, text, cmd);

            }
        });


    }

    //真正的发送消息
    private void realSend(List<TIMUserProfile> timUserProfiles, final String text, final int cmd) {
        //因为获取信息的时候 只传入了只有一个元素的集合，所以到这只能拿到一个用户的信息
        final TIMUserProfile profile = timUserProfiles.get(0);

        //发送普通消息
        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eGroupMsg, profile.getIdentifier(), text), new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                String grade = null;
                String adouId;
                String nickname;
                //发送成功之后，加入到listview中去

                TextMsgInfo textMsgInfo = new TextMsgInfo();
                if (sendserId.equals(AdouApplication.getApp().getAdouTimUserProfile().getProfile().getIdentifier())) {
                    grade = AdouApplication.getApp().getAdouTimUserProfile().getGrade() + "";
                    adouId = AdouApplication.getApp().getAdouTimUserProfile().getProfile().getIdentifier();
                    nickname = AdouApplication.getApp().getAdouTimUserProfile().getProfile().getNickName();
                } else {
                    byte[] bytes = profile.getCustomInfo().get(CustomTimConstant.INFO_GRADE);
                    if (bytes != null) {
                        grade = new String(bytes);
                    }
                    nickname = profile.getNickName();

                }
                if (TextUtils.isEmpty(grade)) {
                    grade = "0";
                }
                adouId = AdouApplication.getApp().getAdouTimUserProfile().getProfile().getIdentifier();
                textMsgInfo.setGrade(Integer.parseInt(grade));
                textMsgInfo.setText(text);
                textMsgInfo.setNickname(TextUtils.isEmpty(nickname) ? sendserId : nickname);
                textMsgInfo.setAdouID(adouId);


                //成功的时候怎么办 ，如果是弹幕的话就执行弹幕，如果不是弹幕 就不执行
                if (cmd == CustomTimConstant.DANMU_MSG) {
                    //TODO
                    String newMsg = text.substring(CustomTimConstant.TYPE_DAN.length(), text.length());
                    String avatar = AdouApplication.getApp().getAdouTimUserProfile().getProfile().getFaceUrl();
                    DanmuMsgInfo danmuMsgInfo = new DanmuMsgInfo();
                    danmuMsgInfo.setAdouID(adouId);
                    danmuMsgInfo.setAvatar(avatar);
                    danmuMsgInfo.setGrade(Integer.parseInt(grade));
                    danmuMsgInfo.setText(newMsg);

                    danmuView.addDanmu(danmuMsgInfo);
                    textMsgInfo.setText(newMsg);
                }
                //更新列表
                lmlv.addMsg(textMsgInfo);

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("发送失败，错误信息" + errMsg + "错误码" + errCode);
            }
        });
    }

    //考虑到联机的发送礼物
    public void sendGift() {
        //在第一次点的时候开始计时
        if (firstSendTimeMillion == 0) {
            //第一次点击不是连发，设置给giftitem
            availableGiftItem.setIsRepeat(false);
            //拿到第一次点的时间
            firstSendTimeMillion = System.currentTimeMillis();
            repeatGiftTimer.sendEmptyMessage(FIRST_GIFT_SEND_FLAG);
            //再执行动画
            availableGiftItem.setVisibility(View.VISIBLE);
            availableGiftItem.startAnimate();
        } else {//如果属于连击的话,需要把倒计时再从10开始倒数，并且增加礼物数
            //属于连发
            availableGiftItem.setIsRepeat(true);
            availableGiftItem.repeatSend();//连发操作
            //清空两个handler的处理
            repeatGiftTimer.removeMessages(FIRST_GIFT_SEND_FLAG);
            repeatGiftTimer.removeMessages(REPEAT_GIFT_SEND_FLAG);
            repeatGiftTimer.sendEmptyMessage(REPEAT_GIFT_SEND_FLAG);
            repeatTimeLimit = 10;
        }
    }

    private void addHeart() {
        //添加一一颗心
        heartLayout.addHeart(generateColor());
    }

    private int generateColor() {
        int rgb = Color.rgb(heartColorRandom.nextInt(255), heartColorRandom.nextInt(255), heartColorRandom.nextInt(255));
        return rgb;
    }

    //开始心形动画,在零秒之后每隔一秒自动冒心
    private void startHeartAnim() {
        heartTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {

                        heartLayout.addHeart(generateColor());
                    }
                });

            }
        }, 0, 1000);

    }

}

