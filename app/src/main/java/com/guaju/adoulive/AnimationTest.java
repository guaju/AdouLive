package com.guaju.adoulive;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.guaju.adoulive.widget.danmu.DanmuItemView;

/**
 * Created by guaju on 2018/1/16.
 */

public class AnimationTest extends Activity{

    private DanmuItemView dd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        dd = findViewById(R.id.dd);
        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        int width = defaultDisplay.getWidth();

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        dd.measure(w, h);
        int width1= dd.getMeasuredWidth();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(dd,"translationX",width,-width1);
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }
}
