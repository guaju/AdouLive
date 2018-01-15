package com.guaju.adoulive.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/15.
 */

public class BottomChatSwitchLayout extends FrameLayout {
    LayoutInflater inflater;
    private CheckBox checkBox;
    private EditText et_content;
    private TextView tv_send;
    private  OnMsgSendListener mListener;

    public BottomChatSwitchLayout(@NonNull Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();

    }

    private void init() {
        View view = inflater.inflate(R.layout.bottom_chat, this, true);
        checkBox = view.findViewById(R.id.checkbox);
        et_content = view.findViewById(R.id.et_content);
        et_content.setHint("开始您的聊天吧~~");
        tv_send = view.findViewById(R.id.tv_send);
        tv_send.setClickable(true);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //当被选中了,说明是弹幕
                if (b) {
                    et_content.setHint("开启您的弹幕聊骚吧~~");

                } else {
                    //普通的消息
                    et_content.setHint("开始您的聊天吧~~");
                }
            }
        });
        tv_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et_content.getText().toString().trim();
                if (checkBox.isChecked()) {
                    //弹幕
                    if (mListener!=null){
                        mListener.danmu(text);

                    }
                } else {
                    //普通消息
                    if (mListener!=null){

                        mListener.sendMsg(text);
                    }
                }
            }
        });
    }


    public BottomChatSwitchLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    public interface OnMsgSendListener {
        void sendMsg(String text);//普通消息

        void danmu(String text);  //弹幕
    }

    public void setOnMsgSendListener(OnMsgSendListener listener){
        mListener=listener;
    }
}
