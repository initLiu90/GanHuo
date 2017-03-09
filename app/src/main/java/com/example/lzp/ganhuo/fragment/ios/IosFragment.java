package com.example.lzp.ganhuo.fragment.ios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.fragment.BaseFragment;

/**
 * Created by lzp on 2017/3/8.
 */

public class IosFragment extends BaseFragment {
    public static final String TAG = IosFragment.class.getSimpleName();
    public static IosFragment sInstance = null;

    public static BaseFragment getInstance() {
        if (sInstance == null) {
            sInstance = new IosFragment();
        }
        return sInstance;
    }

    @Override
    public int getTitle() {
        return R.string.tab_ios;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ios, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
