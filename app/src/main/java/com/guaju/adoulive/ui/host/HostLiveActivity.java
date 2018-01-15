package com.guaju.adoulive.ui.host;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.utils.ToastUtils;
import com.guaju.adoulive.widget.BottomChatSwitchLayout;
import com.guaju.adoulive.widget.BottomSwitchLayout;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.view.AVRootView;
import com.tencent.livesdk.ILVLiveManager;

/**
 * Created by guaju on 2018/1/8.
 */

public class HostLiveActivity extends Activity implements HostLiveContract.View{

    private AVRootView avRootView;
    private ImageView iv_switch_camera;
    private BottomSwitchLayout bottomswitchlayout;
    private HostPresenter presenter;
    private Toolbar toolbar;
    private int roomId;
    private BottomChatSwitchLayout chatswitchlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_live);
        initView();
        setBottomSwitchListener();
        //设置默认状态
        setDefultStatus();

        initToolbar();
        initListener();
        initPresenter();
        initCreateHost();


    }

    private void setDefultStatus() {
        chatswitchlayout.setVisibility(View.INVISIBLE);
        bottomswitchlayout.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        //iv_close

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
        if (intent!=null){
            roomId = intent.getIntExtra("roomId", -1);
        }
        presenter.createHost(roomId);
    }

    private void initPresenter() {
        this.presenter=new HostPresenter(this);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        avRootView = findViewById(R.id.arv_root);
        iv_switch_camera = findViewById(R.id.iv_switch_camera);
        bottomswitchlayout = findViewById(R.id.bottomswitchlayout);
        chatswitchlayout = findViewById(R.id.chatswitchlayout);
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

    public  void  quitRoom(){
        ILVLiveManager.getInstance().quitRoom(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                ToastUtils.show("退出直播成功");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                ToastUtils.show("退出直播失败，错误码"+errCode+"错误信息："+errMsg);
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
        });

    }

}
