package com.guaju.adoulive.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/15.
 */

public class BottomSwitchLayout extends FrameLayout implements View.OnClickListener {
    LayoutInflater inflater;
    private ImageView iv_switch_chat;
    private ImageView iv_switch_close;
    OnSwitchListener mListener;


    public BottomSwitchLayout(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public BottomSwitchLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        //导入布局
        View v = inflater.inflate(R.layout.bottom_chat_or_close, this, true);
        iv_switch_chat = v.findViewById(R.id.iv_switch_chat);
        iv_switch_close = v.findViewById(R.id.iv_switch_close);
        iv_switch_chat.setOnClickListener(this);
        iv_switch_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_switch_chat:
                //切换到聊天布局
                if (mListener!=null){
                    mListener.onChat();
                }
                break;
            case R.id.iv_switch_close:
                //退出直播
                if (mListener!=null){
                    mListener.onClose();
                }
                break;
            default:
                break;
        }
    }

    public  void setOnSwitchListener(OnSwitchListener listener){
        mListener=listener;
    }

    public interface OnSwitchListener{
        void onChat();
        void onClose();
    }
}
