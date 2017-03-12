package com.example.lzp.ganhuo.fragment.today;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.adapter.TodayRecyclerViewAdapter;
import com.example.lzp.ganhuo.fragment.BaseFragment;
import com.example.lzp.ganhuo.net.MyVolley;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzp on 2017/3/8.
 */

public class TodayFragment extends BaseFragment implements TodayContract.View, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = TodayFragment.class.getSimpleName();

    private TodayContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TodayRecyclerViewAdapter mAdapter;

    @Override
    public int getTitle() {
        return R.string.tab_day;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TodayPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.today_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.today_recycler);
        initView();
        requestTodayData();
    }

    private void initView() {
        mSwipRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TodayRecyclerViewAdapter(getContext(), todayInterface);
        mRecyclerView.setAdapter(mAdapter);
    }

    private TodayInterface todayInterface = new TodayInterface() {
        @Override
        public void showImage(String imageUrl) {
            Log.e("Test", "image=" + imageUrl);
        }

        @Override
        public void showContent(String contentUrl) {
            Log.e("Test", "content=" + contentUrl);
        }
    };

    @Override
    public void showTodayData(Today data) {
        mAdapter.setData(data);
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        if (mSwipRefreshLayout != null) {
            mSwipRefreshLayout.setRefreshing(active);
        }
    }

    @Override
    public void onRefresh() {
        requestTodayData();
    }

    private void requestTodayData() {
        if (getUserVisibleHint()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String strDate = format.format(date);
            mPresenter.requestTodayData("2017/03/10");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSwipRefreshLayout.setOnRefreshListener(null);
        mPresenter.cancleRequest();
    }
}
