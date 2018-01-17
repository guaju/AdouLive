package com.guaju.adoulive.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.Gift;
import com.guaju.adoulive.widget.gift.GiftGridView;

import java.util.ArrayList;

/**
 * Created by guaju on 2018/1/3.
 */

public class GiftSendDialog implements View.OnClickListener {
    //gridview集合
    ArrayList<GiftGridView> gridViewLists=new ArrayList<>();
    ArrayList<Gift> allGifts=new ArrayList<>();

    private TextView tv_photo;
    private TextView tv_camera;
    private LinearLayout ll_cancel;


    Activity activity;
    LayoutInflater inflater;

    private View v;
    Dialog dialog;
    private WindowManager wm;
    private Display display;
    private ViewGroup.LayoutParams layoutParams;
    private ViewPager vp_gift_send;
    private ImageView iv_indicator0;
    private ImageView iv_indicator1;
    private Button bt_send_gift;

    public GiftSendDialog(@NonNull Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        init();
        initListener();
    }

    private void initListener() {
        vp_gift_send.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (0==position){
                    iv_indicator0.setBackgroundResource(R.mipmap.indicator_selected);
                    iv_indicator1.setBackgroundResource(R.mipmap.indicator_normal);
                } else if (1==position){
                    iv_indicator0.setBackgroundResource(R.mipmap.indicator_normal);
                    iv_indicator1.setBackgroundResource(R.mipmap.indicator_selected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public void show() {

        dialog.show();

    }

    private void init() {
        initAllGift();
        
        wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        inflater = LayoutInflater.from(activity);
        v = inflater.inflate(R.layout.dialog_gift_send, null, false);
        //初始化自己的id
        vp_gift_send = v.findViewById(R.id.vp_gift_send);
        iv_indicator0 = v.findViewById(R.id.iv_indicator0);
        iv_indicator1 = v.findViewById(R.id.iv_indicator1);
        bt_send_gift = v.findViewById(R.id.bt_send_gift);

        //准备两个gridview
        initGridView();



        dialog.setContentView(v);
        //通过window设置dialog的宽高和位置
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = display.getWidth();
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
    }

    private void initGridView() {
        //准备viewpager中的两个gridview
        ArrayList<Gift> gifts0 =new ArrayList<>();
        ArrayList<Gift> gifts1 =new ArrayList<>();
        int startIndex=0;
        int  endIndex=8;
        //将大集合分成两个集合
        gifts0.addAll(allGifts.subList(startIndex, endIndex));
        gifts1.addAll(allGifts.subList(endIndex, allGifts.size()));
        GiftGridView giftGridView0 = new GiftGridView(activity);
        giftGridView0.setGiftData(gifts0);

        GiftGridView giftGridView1 = new GiftGridView(activity);
        giftGridView1.setGiftData(gifts1);

        gridViewLists.add(giftGridView0);
        gridViewLists.add(giftGridView1);
        //创建viewpager适配器
        GiftViewPageAdapter giftViewPageAdapter = new GiftViewPageAdapter();
        vp_gift_send.setAdapter(giftViewPageAdapter);

    }

    private void initAllGift() {
        allGifts.add(Gift.gift0);
        allGifts.add(Gift.gift1);
        allGifts.add(Gift.gift2);
        allGifts.add(Gift.gift3);
        allGifts.add(Gift.gift4);
        allGifts.add(Gift.gift5);
        allGifts.add(Gift.gift6);
        allGifts.add(Gift.gift7);
        allGifts.add(Gift.gift8);
        allGifts.add(Gift.gift9);
        allGifts.add(Gift.gift10);
        allGifts.add(Gift.gift11);
        allGifts.add(Gift.giftEmpty);
        allGifts.add(Gift.giftEmpty);
        allGifts.add(Gift.giftEmpty);
        allGifts.add(Gift.giftEmpty);
    }

    public GiftSendDialog(@NonNull Activity activity, int themeResId) {
        this.activity = activity;
        //把dialog实例化
        dialog = new Dialog(activity, themeResId);
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_photo:

                break;
            case R.id.tv_camera:

                break;
            case R.id.iv_cancel:

            default:
                break;
        }
    }


    class GiftViewPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return gridViewLists.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            GiftGridView giftGridView = gridViewLists.get(position);
            if (giftGridView.getParent()==null){
                container.addView(giftGridView);
            }
            return  giftGridView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView(gridViewLists.get(position));
        }
    }
}
