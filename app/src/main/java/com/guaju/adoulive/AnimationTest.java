package com.guaju.adoulive;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.guaju.adoulive.widget.danmu.DanmuItemView;
import com.guaju.adoulive.widget.gift.GiftItem;
import com.guaju.adoulive.widget.gift.GiftSendDialog;

/**
 * Created by guaju on 2018/1/16.
 */

public class AnimationTest extends Activity{

    private DanmuItemView dd;
    private GiftItem giftItem;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//        giftItem = findViewById(R.id.gift);
//        imageView = findViewById(R.id.iv_gift_icon);
//        giftItem.startAnimate();
////        giftItem.startAnimate();
        GiftSendDialog giftSendDialog = new GiftSendDialog(this,R.style.custom_dialog);
        giftSendDialog.show();
    }
}
