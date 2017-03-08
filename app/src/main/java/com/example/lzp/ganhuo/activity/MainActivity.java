package com.example.lzp.ganhuo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.adapter.GHPageAdapter;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private GHPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);
        initView();
        initViewPager();
        initTabLayout();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_day), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_android));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_ios));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_fuli));
        mTabLayout.setupWithViewPager(mPager);
    }

    private void initViewPager() {
        mPageAdapter = new GHPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);
    }
}
