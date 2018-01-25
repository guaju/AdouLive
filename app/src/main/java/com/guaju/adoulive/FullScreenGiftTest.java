package com.guaju.adoulive;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;

import com.guaju.adoulive.widget.gift.GiftFullScreenItem;
import com.guaju.adoulive.widget.gift.GiftFullScreenView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.widget.HeartLayout;

/**
 * Created by guaju on 2018/1/16.
 */

public class FullScreenGiftTest extends Activity {


    private GiftFullScreenItem giftfullscreen;
    private Animation animationout;
    private Animation animationstay;
    private Animation animationin;
    private GiftFullScreenView giftfullscreen1;
    private HeartLayout heartLayout;
    Random random = new Random();
    Timer timer = new Timer();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        heartLayout = findViewById(R.id.hearlayout);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addHeart(generateColor(),R.drawable.content,R.drawable.border);

                    }
                });
            }
        }, 0, 1000);


    }

    private int generateColor() {
        int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        return rgb;
    }


}
