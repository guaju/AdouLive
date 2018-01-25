package com.guaju.adoulive.bean;

import com.guaju.adoulive.R;

import java.util.ArrayList;

/**
 * Created by guaju on 2018/1/17.
 */

public class Gift {

    int  giftId;
    public static ArrayList<Gift> allGifts = new ArrayList<>();
    int resId;
    String name;
    int price;
    GiftType type;
    boolean isSelected;


    public Gift(int giftId, int resId, String name, int price, GiftType type, boolean isSelected) {
        this.giftId = giftId;
        this.resId = resId;
        this.name = name;
        this.price = price;
        this.type = type;
        this.isSelected = isSelected;
    }

    public static Gift getGiftByName(String giftName){
        Gift gift=null;
        for (Gift g:allGifts){
            if (giftName.equals(g.getName())){
                   gift=g;
            }
        }
        return gift;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static final Gift gift0 = new Gift(0,R.mipmap.xuegao, "雪糕", 1, GiftType.Repeat, false);
    public static final Gift gift1 = new Gift(1,R.mipmap.qishui, "汽水", 5, GiftType.Repeat, false);
    public static final Gift gift2 = new Gift(2,R.mipmap.juanzhi, "卷纸", 10, GiftType.Repeat, false);
    public static final Gift gift3 = new Gift(3,R.mipmap.zhiwu, "多肉植物", 20, GiftType.Repeat, false);
    public static final Gift gift4 = new Gift(4,R.mipmap.heifengli, "黑凤梨", 50, GiftType.Repeat, false);
    public static final Gift gift5 = new Gift(5,R.mipmap.yusan, "雨伞", 100, GiftType.Repeat, false);
    public static final Gift gift6 = new Gift(6,R.mipmap.shoubiao, "手表", 200, GiftType.Repeat, false);
    public static final Gift gift7 = new Gift(7,R.mipmap.youxiji, "游戏机", 300, GiftType.Repeat, false);
    public static final Gift gift8 = new Gift(8,R.mipmap.xiangji, "相机", 400, GiftType.Repeat, false);
    public static final Gift gift9 = new Gift(9,R.mipmap.chengbao, "城堡", 1000, GiftType.Repeat, false);
    public static final Gift gift10 = new Gift(10,R.mipmap.huojian, "火箭", 2000, GiftType.Repeat, false);
    public static final Gift gift11 = new Gift(11,R.mipmap.xuegao, "超跑", 8888, GiftType.FullScreen, false);
    public static final Gift giftEmpty = new Gift(12, 0,"空", 0, GiftType.Empty, false);

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public Gift() {
    }


    public enum GiftType {
        //连发，全屏礼物
        Repeat, FullScreen, Empty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public GiftType getType() {
        return type;
    }

    public void setType(GiftType type) {
        this.type = type;
    }

    //让所有设置不选中
    public static void setUnSelected() {
       for (Gift g:allGifts){
           g.setSelected(false);
       }
    }
     //改成静态代码块，他会在静态变量初始化之后直接调用
     static  {
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
}
