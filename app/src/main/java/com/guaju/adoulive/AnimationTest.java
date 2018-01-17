package com.guaju.adoulive;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.guaju.adoulive.widget.GiftSendDialog;
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
        GiftSendDialog giftSendDialog = new GiftSendDialog(this,R.style.custom_dialog);
        giftSendDialog.show();
    }
}
