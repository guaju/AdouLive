package com.guaju.adoulive;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.guaju.adoulive.widget.gift.GiftFullScreenItem;

/**
 * Created by guaju on 2018/1/16.
 */

public class FullScreenGiftTest extends Activity {


    private GiftFullScreenItem giftfullscreen;
    private Animation animationout;
    private Animation animationstay;
    private Animation animationin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        giftfullscreen = findViewById(R.id.giftfullscreen);
        giftfullscreen.setVisibility(View.INVISIBLE);
        animationin = AnimationUtils.loadAnimation(this, R.anim.gift_full_screen_in);
        animationstay = AnimationUtils.loadAnimation(this, R.anim.gift_full_screen_stay);
        animationout = AnimationUtils.loadAnimation(this, R.anim.gift_full_screen_out);

        giftfullscreen.setAnimation(animationin);
//        giftfullscreen.setAnimation(animationout);
        animationin.start();
        animationin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                    giftfullscreen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //此操作等同于创建出一个主线程的handler 然后让handler去发送handler
                giftfullscreen.post(new Runnable() {
                    @Override
                    public void run() {
                       giftfullscreen.setAnimation(animationstay);

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
                 giftfullscreen.post(new Runnable() {
                     @Override
                     public void run() {
                         giftfullscreen.setAnimation(animationout);
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
                giftfullscreen.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        giftfullscreen.cancleDrawableAnim();

    }
}
