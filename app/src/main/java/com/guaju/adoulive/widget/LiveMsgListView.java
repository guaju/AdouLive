package com.guaju.adoulive.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.guaju.adoulive.R;
import com.guaju.adoulive.bean.TextMsgInfo;

import java.util.ArrayList;

/**
 * Created by guaju on 2018/1/15.
 */

public class LiveMsgListView extends ListView {
    ArrayList<TextMsgInfo> mList;
    private MsgAdapter msgAdapter;

    public LiveMsgListView(Context context) {
        super(context);
        init();
    }
    //设置数据源
    public void setData(ArrayList<TextMsgInfo> list){
        mList=list;
        msgAdapter = new MsgAdapter();
        setAdapter(msgAdapter);
    }

    //设置本listview的默认值
    private void init() {
        //设置分割线高度为0 
        setDividerHeight(0);

    }

    public LiveMsgListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    class MsgAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList!=null){
                return mList.size();
            }else{
                return 0;
            }
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(getContext(), R.layout.item_msg_text,null);
                viewHolder=new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.setContent(mList.get(i));

            return convertView;
        }
    }

    class ViewHolder{
      TextView tv_msg;
      public ViewHolder(View itemView){
          tv_msg=itemView.findViewById(R.id.tv_msg);
        }

      public void setContent(TextMsgInfo info){
          String adouID = info.getAdouID();
          String text = info.getText();
          //text  中用户名置为蓝色，把消息置为白色

          SpannableStringBuilder ssb=new SpannableStringBuilder("");//定义一个断点的sb
          //用户的spannable
          SpannableString adouss = new SpannableString(adouID+":");
          int startIndex=0;
          int endIndex= adouID.length()+1;
          adouss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)),startIndex,endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
          //消息字段
          SpannableString textss = new SpannableString(text);
          int startIndex1=0;
          int endIndex1= text.length();
          textss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)),startIndex1,endIndex1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

          //加在一起
          ssb.append(adouss);
          ssb.append(textss);
          tv_msg.setText(ssb);
      }

    }
    //添加消息
    public  void addMsg(TextMsgInfo msgInfo){
        //更新数据
        mList.add(msgInfo);
        msgAdapter.notifyDataSetChanged();


    }

}


