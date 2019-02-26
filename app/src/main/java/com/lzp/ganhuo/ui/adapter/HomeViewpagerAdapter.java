package com.lzp.ganhuo.ui.adapter;

import android.os.Bundle;

import com.lzp.ganhuo.ui.fragment.RecyclverViewFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeViewpagerAdapter extends FragmentPagerAdapter {
    private String[] mData;

    public HomeViewpagerAdapter(FragmentManager fm, String[] data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(RecyclverViewFragment.KEY_CATEGORY, mData[position]);
        return RecyclverViewFragment.newFragment(bundle);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData == null ? "" : mData[position];
    }
}
