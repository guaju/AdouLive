package com.guaju.adoulive.widget.gift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/24.
 */

public class GiftFullScreenView extends FrameLayout{

    private LayoutInflater inflater;
    private GiftFullScreenItem item;
    private Animation animationin;
    private Animation animationstay;
    private Animation animationout;

    public GiftFullScreenView(@NonNull Context context) {
        super(context);
        init();
    }

    public GiftFullScreenView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.gift_full_screen_view, this, true);
        item = v.findViewById(R.id.giftfullscreenitem);

    }
    //保时捷跑起来
    public  void  porcheGo(){
        item.porcheGo();
    }
    //获得可用的条目
    public GiftFullScreenItem getAvaliableItem(){
       if (item.getVisibility()==View.INVISIBLE){
           return item;
       }else{
           return null;
       }

    }
}
