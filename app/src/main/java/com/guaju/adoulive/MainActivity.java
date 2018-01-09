package com.guaju.adoulive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.guaju.adoulive.ui.createlive.CreateLiveActivity;
import com.guaju.adoulive.ui.home.HomeFragment;
import com.guaju.adoulive.ui.mine.MineFragment;

public class MainActivity extends FragmentActivity {

//    private FrameLayout tabcontent;
    private FragmentTabHost tabHost;
    private FragmentManager sfm;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sfm = getSupportFragmentManager();
        initView();
        updateView();
        initToolbar("首页");

    }

    private void updateView() {
        Intent intent = getIntent();
        if (intent!=null){
            //说明从别的activity跳转过来
            String from = intent.getStringExtra("from");
            if ("EditProfileActivity".equals(from)){
                //说明从编辑页面跳转过来的
                tabHost.setCurrentTab(2);
            }

        }
    }

    public void initToolbar(String str) {
        toolbar.setTitle(str);

     }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
//        tabcontent = findViewById(R.id.tabcontent);
        tabHost = findViewById(R.id.tabhost);
        tabHost.setup(this,sfm,R.id.tabcontent);
        //创建选项卡
        TabHost.TabSpec tabSpec_home = tabHost.newTabSpec("home").setIndicator(getIndicatorView("home"));//已经创建一个首页的选项卡
        TabHost.TabSpec tabSpec_create = tabHost.newTabSpec("create").setIndicator(getIndicatorView("create"));//已经创建一个首页的选项卡
        TabHost.TabSpec tabSpec_mine =  tabHost.newTabSpec("mine").setIndicator(getIndicatorView("mine"));//已经创建一个首页的选项卡
        //添加选项卡
        tabHost.addTab(tabSpec_home,HomeFragment.class,null);
        //发起直播按钮
        tabHost.addTab(tabSpec_create,null,null);
        tabHost.addTab(tabSpec_mine,MineFragment.class,null);
        tabHost.getTabWidget().setDividerDrawable(R.color.transprant);
        //定义事件将tabspec事件拦截
        tabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent= new Intent(MainActivity.this,CreateLiveActivity.class);
            startActivity(intent);
            }
        });

    }

    private View getIndicatorView(String tag) {
        View v = LayoutInflater.from(this).inflate(R.layout.tabspec, null, false);
        ImageView iv = v.findViewById(R.id.iv);
        if ("home".equals(tag)){
            iv.setBackgroundResource(R.drawable.selector_tab_home);
        }else if ("mine".equals(tag)){
            iv.setBackgroundResource(R.drawable.selector_tab_mine);
        }else if ("create".equals(tag)){
            iv.setBackgroundResource(R.mipmap.create);
        }
        return v;
    }
}
