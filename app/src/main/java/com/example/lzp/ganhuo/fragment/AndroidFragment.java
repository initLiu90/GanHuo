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

public class AndroidFragment extends BaseFragment {
    public static final String TAG = AndroidFragment.class.getSimpleName();
    public static AndroidFragment sInstance = null;

    public static BaseFragment getInstance() {
        if (sInstance == null) {
            sInstance = new AndroidFragment();
        }
        return sInstance;
    }

    @Override
    public int getTitle() {
        return R.string.tab_android;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
