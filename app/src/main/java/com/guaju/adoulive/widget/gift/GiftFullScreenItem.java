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

public class GiftFullScreenItem extends FrameLayout {

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

        porche_back = v.findViewById(R.id.porche_back);
        porche_front = v.findViewById(R.id.porche_front);
        porche_back_anim = (AnimationDrawable) porche_back.getBackground();
        porche_back_front = (AnimationDrawable) porche_front.getBackground();
        porche_back_anim.start();
        porche_back_front.start();
        initAnimation();


    }

    private void initAnimation() {
        animationin = AnimationUtils.loadAnimation(getContext(), R.anim.gift_full_screen_in);
        animationstay = AnimationUtils.loadAnimation(getContext(), R.anim.gift_full_screen_stay);
        animationout = AnimationUtils.loadAnimation(getContext(), R.anim.gift_full_screen_out);
        animationin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //此操作等同于创建出一个主线程的handler 然后让handler去发送handler
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      startAnimation(animationout);
                    }
                },2000);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        animationstay.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startAnimation(animationout);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        //最后一个移出动画
        animationout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.INVISIBLE);

                //从消息缓存队列中去取消息，由于消息属于GiftFullScreenView持有，所以现在需要回调
                mOnCompleted.onCompleted();

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

    public void cancleDrawableAnim() {
        porche_back_anim.stop();
        porche_back_front.stop();
    }

    //保时捷跑起来
    public void porcheGo() {
        startAnimation(animationin);
    }


    OnGiftAnimationCompleted mOnCompleted;

    public void setOnGiftAnimationCommpleted(OnGiftAnimationCompleted oo) {
        mOnCompleted = oo;
    }

    public interface OnGiftAnimationCompleted {
        //当当前动画执行完后，去加载缓存数据
        void onCompleted();
    }
}
