package com.example.lzp.ganhuo.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.adapter.GHPageAdapter;
import com.example.lzp.ganhuo.fragment.BaseFragment;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private GHPageAdapter mPageAdapter;
    private FloatingActionButton mFABtnDate, mFABtnAdd, mFABDec;
    private LinearLayout mLayoutDate;

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
        mFABtnDate = (FloatingActionButton) findViewById(R.id.date);
        mFABtnDate.show();
        mFABtnDate.setOnClickListener(clickListener);

        mLayoutDate = (LinearLayout) findViewById(R.id.date_layout);
        mLayoutDate.setVisibility(View.GONE);

        mFABtnAdd = (FloatingActionButton) findViewById(R.id.date_add);
        mFABtnAdd.setOnClickListener(clickListener);

        mFABDec = (FloatingActionButton) findViewById(R.id.date_decrease);
        mFABDec.setOnClickListener(clickListener);
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mFABtnDate) {
                updateLayoutDateVisible();
            } else if (v == mFABtnAdd) {
                add();
            } else if (v == mFABDec) {
                decrease();
            }
        }
    };

    public void updateLayoutDateVisible() {
        ObjectAnimator animator = null;
        if (mLayoutDate.getVisibility() == View.GONE) {
            animator = ObjectAnimator.ofFloat(mLayoutDate, "alpha", 0f, 1f);
        } else {
            animator = ObjectAnimator.ofFloat(mLayoutDate, "alpha", 1f, 0f);
        }
        animator.setDuration(100);
        animator.addListener(animatorListener);
        animator.start();
    }

    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mLayoutDate.getVisibility() == View.VISIBLE) {
                mLayoutDate.setVisibility(View.GONE);
            } else {
                mLayoutDate.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void add() {
        BaseFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.add();
        }
    }

    private void decrease() {
        BaseFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.decrease();
        }
    }

    private BaseFragment getCurrentFragment() {
        int curItem = mPager.getCurrentItem();
        Fragment fragment = mPageAdapter.getItem(curItem);
        if (fragment != null && fragment instanceof BaseFragment) {
            return (BaseFragment) fragment;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLayoutDate.clearAnimation();
    }
}
