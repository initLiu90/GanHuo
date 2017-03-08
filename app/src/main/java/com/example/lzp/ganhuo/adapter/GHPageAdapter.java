package com.example.lzp.ganhuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lzp.ganhuo.app.BaseApplication;
import com.example.lzp.ganhuo.fragment.AndroidFragment;
import com.example.lzp.ganhuo.fragment.BaseFragment;
import com.example.lzp.ganhuo.fragment.FuliFragment;
import com.example.lzp.ganhuo.fragment.IosFragment;
import com.example.lzp.ganhuo.fragment.TodayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzp on 2017/3/8.
 */

public class GHPageAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments = new ArrayList<>();

    public GHPageAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(TodayFragment.getInstance());
        fragments.add(AndroidFragment.getInstance());
        fragments.add(IosFragment.getInstance());
        fragments.add(FuliFragment.getInstance());
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        BaseFragment fragment = fragments.get(position);
        int resId = fragment.getTitle();
        return BaseApplication.sApplication.getResources().getString(resId);
    }
}
