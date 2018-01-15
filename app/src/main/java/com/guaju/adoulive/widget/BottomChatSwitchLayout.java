package com.guaju.adoulive.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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

    public BottomChatSwitchLayout(@NonNull Context context) {
        super(context);
        inflater=LayoutInflater.from(context);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.bottom_chat, this, true);
        checkBox = view.findViewById(R.id.checkbox);
        et_content = view.findViewById(R.id.et_content);
        tv_send = view.findViewById(R.id.tv_send);
    }


    public BottomChatSwitchLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater=LayoutInflater.from(context);
        init();
    }
}
