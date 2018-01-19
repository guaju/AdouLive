package com.guaju.adoulive.widget.gift;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.Gift;

import java.util.ArrayList;

/**
 * Created by guaju on 2018/1/17.
 */

public class GiftGridView extends GridView {
    //用来装载数据的集合
    ArrayList<Gift> giftLists = new ArrayList<>();

    LayoutInflater inflater;
    private GiftGridAdapter giftGridAdapter;

    SetGiftDefault mSetGiftDefault;

    //在java代码中创建使用的构造方法
    public GiftGridView(Context context, SetGiftDefault giftDefault) {
        super(context);
        inflater = LayoutInflater.from(context);
        mSetGiftDefault = giftDefault;
        init();
    }


    private void init() {
        //设置gridview的属性
        setNumColumns(4); //设置有四列
//        setGiftData();
        giftGridAdapter = new GiftGridAdapter();
        setAdapter(giftGridAdapter);

    }

    //在布局中使用时 自动调用的构造方法
    public GiftGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }


    //提供方法传入礼物数据
    public void setGiftData(ArrayList<Gift> gifts) {
        //先释放下资源
        giftLists.clear();
        //再赋值
        giftLists = gifts;
        //更新数据
        giftGridAdapter.notifyDataSetChanged();
    }


    //定义礼物的适配器
    class GiftGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return giftLists.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gift, null, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            final Gift gift = giftLists.get(pos);
            //设置gridview item点击事件
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //设置选中礼物
                    if (gift.isSelected()) {
                        gift.setSelected(false);
                    } else {
                        //让其他礼物不选中
                        mSetGiftDefault.setOnSelected(gift);
                    }

                }
            });
            viewHolder.bindData(gift);


            return convertView;
        }

        class ViewHolder {

            private final ImageView iv_gift_icon;
            private final TextView tv_gift_name;
            private final TextView tv_gift_price;
            private final ImageView iv_select;

            public ViewHolder(View itemView) {
                iv_gift_icon = itemView.findViewById(R.id.iv_gift_icon);
                tv_gift_name = itemView.findViewById(R.id.tv_gift_name);
                tv_gift_price = itemView.findViewById(R.id.tv_gift_price);
                iv_select = itemView.findViewById(R.id.iv_select);

            }

            public void bindData(Gift gift) {
                if (gift != null) {
                    if (gift.getResId() != 0) {
                        iv_gift_icon.setBackgroundResource(gift.getResId());
                    } else {
                        iv_gift_icon.setBackgroundColor(getContext().getResources().getColor(R.color.transprant));
                    }
                    if (!TextUtils.isEmpty(gift.getName())) {
                        tv_gift_name.setText(gift.getName());
                    }
                    //因为价格是int类型
                    tv_gift_price.setText(gift.getPrice() + "斗币");
                    if (gift.isSelected()) {
                        iv_select.setBackgroundResource(R.mipmap.right);
                    } else {
                        iv_select.setBackgroundColor(getContext().getResources().getColor(R.color.transprant));
                    }
                }

            }


        }
    }

    //让所有礼物不选中
    public interface SetGiftDefault {
        void setOnSelected(Gift gift);
    }
    //设置某一个礼物选中
    public void setGiftSelected(Gift gift) {
        for (Gift g : giftLists) {
            if (g.getGiftId() == gift.getGiftId()) {
                g.setSelected(true);
                //刷新数据
            } else {
                g.setSelected(false);
            }
            giftGridAdapter.notifyDataSetChanged();
        }
    }

}
