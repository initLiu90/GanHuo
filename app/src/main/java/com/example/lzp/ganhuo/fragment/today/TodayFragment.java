package com.example.lzp.ganhuo.fragment.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.activity.BrowserActivity;
import com.example.lzp.ganhuo.activity.ImageGallery.ImageGalleryActivity;
import com.example.lzp.ganhuo.activity.MainActivity;
import com.example.lzp.ganhuo.adapter.TodayRecyclerViewAdapter;
import com.example.lzp.ganhuo.app.BaseApplication;
import com.example.lzp.ganhuo.data.today.TodayLocalDataSource;
import com.example.lzp.ganhuo.data.today.TodayRepository;
import com.example.lzp.ganhuo.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzp on 2017/3/8.
 */

public class TodayFragment extends BaseFragment implements TodayContract.View, SwipeRefreshLayout
        .OnRefreshListener {
    public static final String TAG = TodayFragment.class.getSimpleName();

    private TodayContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TodayRecyclerViewAdapter mAdapter;
    private String mCurrentDate;
    private View mConverView;

    @Override
    public int getTitle() {
        return R.string.tab_day;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mPresenter = new TodayPresenter(TodayRepository.getInstance(TodayLocalDataSource
                .getInstance(BaseApplication.sApplication)), this);
        //以下是为了防止滑动到别的页面后，在回到当前页面从新加载数据。
        //因为fragment没有被销毁，所以fragment中的实例都还存在，所以可以直接加载原来实例。
        if (mConverView == null) {
            mConverView = inflater.inflate(R.layout.fragment_today, container, false);
            doOnViewCreated(mConverView);
        } else {
            ViewGroup parent = (ViewGroup) mConverView.getParent();
            if (parent != null) {
                parent.removeView(mConverView);
            }
        }
        return mConverView;
    }

    private void doOnViewCreated(View view) {
        mSwipRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.today_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.today_recycler);
        initView();

        if (TextUtils.isEmpty(mCurrentDate)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String strDate = format.format(date);
            mCurrentDate = strDate;
        }
        requestTodayData(mCurrentDate);
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
            Intent intent = new Intent(TodayFragment.this.getActivity(), ImageGalleryActivity
                    .class);
            intent.putExtra(ImageGalleryActivity.KEY_CUR_URL, imageUrl);
            intent.putExtra(ImageGalleryActivity.KEY_CUR_DATE, mCurrentDate);
            TodayFragment.this.getActivity().startActivity(intent);
        }

        @Override
        public void showContent(String contentUrl) {
            Intent intent = new Intent(TodayFragment.this.getActivity(), BrowserActivity.class);
            intent.putExtra(BrowserActivity.KEY_URL, contentUrl);
            TodayFragment.this.getActivity().startActivity(intent);
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
        requestTodayData(mCurrentDate);
    }

    private void requestTodayData(String date) {
        if (getUserVisibleHint()) {
            mCurrentDate = date;
            mPresenter.requestTodayData(date);
        }
    }

    @Override
    public void add() {
        if (!TextUtils.isEmpty(mCurrentDate)) {
            String[] strs = mCurrentDate.split("/");
            int year = Integer.valueOf(strs[0]);
            int month = Integer.valueOf(strs[1]);
            int day = Integer.valueOf(strs[2]);

            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String strDate = format.format(new Date());
            strs = strDate.split("/");
            int curYear = Integer.valueOf(strs[0]);
            int curMonth = Integer.valueOf(strs[1]);
            int curDay = Integer.valueOf(strs[2]);

            if (year == curYear && month == curMonth && day == curDay) {//已经是当天，不能在增加了
                return;
            }
            if (year < curYear || (year == curYear && month < curMonth) || (year == curYear && month == curMonth && day < curDay)) {
                day++;
                mCurrentDate = year + "/" + month + "/" + day;
                requestTodayData(mCurrentDate);
            }
        }
        ((MainActivity) getActivity()).updateLayoutDateVisible();
    }

    @Override
    public void decrease() {
        if (!TextUtils.isEmpty(mCurrentDate)) {
            String[] strs = mCurrentDate.split("/");
            int year = Integer.valueOf(strs[0]);
            int month = Integer.valueOf(strs[1]);
            int day = Integer.valueOf(strs[2]);

            if (day == 1) {
                if (month == 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                if (month == 2)
                    day = 28;
                else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                    day = 31;
                else
                    day = 30;
            } else {
                day--;
            }
            mCurrentDate = year + "/" + month + "/" + day;
            requestTodayData(mCurrentDate);
        }
        ((MainActivity) getActivity()).updateLayoutDateVisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSwipRefreshLayout.setOnRefreshListener(null);
        mPresenter.stop();
    }
}
