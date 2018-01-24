package com.guaju.adoulive.widget.gift;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.guaju.adoulive.R;

/**
 * Created by guaju on 2018/1/23.
 */

public class GiftFullScreenItem extends FrameLayout{

    private LayoutInflater inflater;
    private ImageView porche_back;
    private ImageView porche_front;
    private AnimationDrawable porche_back_anim;
    private AnimationDrawable porche_back_front;
    private FrameLayout porche;

    private Animation animationin;
    private Animation animationstay;
    private Animation animationout;

    public GiftFullScreenItem(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.gift_full_screen, this, true);
        porche = v.findViewById(R.id.porche);
        porche_back = v.findViewById(R.id.porche_back);
        porche_front = v.findViewById(R.id.porche_front);
        porche_back_anim = (AnimationDrawable) porche_back.getBackground();
        porche_back_front = (AnimationDrawable) porche_front.getBackground();
        porche_back_anim.start();
        porche_back_front.start();
        initAnimation(porche);


    }

    private void initAnimation(final View target) {
        animationin = AnimationUtils.loadAnimation(getContext(), R.anim.gift_full_screen_in);
        animationstay = AnimationUtils.loadAnimation(getContext(), R.anim.gift_full_screen_stay);
        animationout = AnimationUtils.loadAnimation(getContext(), R.anim.gift_full_screen_out);
        animationin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                target.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //此操作等同于创建出一个主线程的handler 然后让handler去发送handler
                post(new Runnable() {
                    @Override
                    public void run() {
                        target.setAnimation(animationstay);

                    }
                });


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationstay.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        target.setAnimation(animationout);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                target.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public GiftFullScreenItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public  void cancleDrawableAnim(){
        porche_back_anim.stop();
        porche_back_front.stop();
    }

    //保时捷跑起来
    public  void  porcheGo(){

        porche.setAnimation(animationin);
        animationin.start();
    }
}
