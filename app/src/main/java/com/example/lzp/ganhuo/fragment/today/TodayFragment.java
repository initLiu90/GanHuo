package com.example.lzp.ganhuo.fragment.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.activity.BrowserActivity;
import com.example.lzp.ganhuo.activity.ImageGallery.ImageGalleryActivity;
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

    @Override
    public int getTitle() {
        return R.string.tab_day;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TodayPresenter(TodayRepository.getInstance(TodayLocalDataSource
                .getInstance(BaseApplication.sApplication)), this);
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
        requestTodayData();
    }

    private void requestTodayData() {
        if (getUserVisibleHint()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String strDate = format.format(date);
//            strDate = "2017/03/16";
            mCurrentDate = strDate;
            mPresenter.requestTodayData(strDate);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSwipRefreshLayout.setOnRefreshListener(null);
        mPresenter.stop();
    }
}
