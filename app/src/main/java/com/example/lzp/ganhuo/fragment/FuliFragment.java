package com.example.lzp.ganhuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzp.ganhuo.R;

/**
 * Created by lzp on 2017/3/8.
 */

public class FuliFragment extends BaseFragment {
    public static final String TAG = FuliFragment.class.getSimpleName();
    public static FuliFragment sInstance = null;
    public static final int TITLE = R.string.tab_fuli;

    public static BaseFragment getInstance() {
        if (sInstance == null) {
            sInstance = new FuliFragment();
        }
        return sInstance;
    }

    @Override
    public int getTitle() {
        return R.string.tab_fuli;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuli, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
