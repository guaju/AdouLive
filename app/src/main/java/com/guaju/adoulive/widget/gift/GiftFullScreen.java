package com.guaju.adoulive.widget.gift;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/23.
 */

public class GiftFullScreen extends FrameLayout{

    private LayoutInflater inflater;
    private ImageView porche_back;
    private ImageView porche_front;

    public GiftFullScreen(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.gift_full_screen, this, true);
        porche_back = v.findViewById(R.id.porche_back);
        porche_front = v.findViewById(R.id.porche_front);
        AnimationDrawable porche_back_anim = (AnimationDrawable) porche_back.getBackground();
        AnimationDrawable porche_back_front = (AnimationDrawable) porche_front.getBackground();
        porche_back_anim.start();
        porche_back_front.start();
    }

    public GiftFullScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
}
