package com.example.lzp.ganhuo.fragment.today;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lzp.ganhuo.data.today.TodayDataSource;
import com.example.lzp.ganhuo.data.today.TodayRepository;
import com.example.lzp.ganhuo.fragment.today.Today;
import com.example.lzp.ganhuo.fragment.today.TodayContract;
import com.example.lzp.ganhuo.net.MyVolley;
import com.example.lzp.ganhuo.util.JsonParser;

import java.lang.ref.WeakReference;

/**
 * Created by SKJP on 2017/3/9.
 */

public class TodayPresenter implements TodayContract.Presenter {
    private static final String TODAY_URL = "http://gank.io/api/day/";
    private static final String REQUEST_TAG = "today";
    private TodayContract.View mTodayView;
    private TodayRepository mTodayRepository;

    public TodayPresenter(TodayRepository todayRepository, TodayContract.View todayView) {
        mTodayView = todayView;
        mTodayRepository = todayRepository;
    }

    /**
     * @param date 2017/08/21
     */
    @Override
    public void requestTodayData(final String date) {
        mTodayView.showLoadingIndicator(true);
        getTodayFromDatabase(date);
    }

    private void getTodayFromNetwork(String date) {
        String url = TODAY_URL + date;
        Log.e("Test","getTodayFromNetwork url"+url);
        ResponseListener listener = new ResponseListener(this, date);
        MyVolley.request(url, REQUEST_TAG, listener, listener);
    }

    private void getTodayFromDatabase(String date) {
        ResponseListener listener = new ResponseListener(this, date);
        mTodayRepository.getToday(date, listener);
    }

    private void processTodayData(String s, String date) {
        Today today = JsonParser.parse2Today(s);
        processTodayData(today, date, true);
    }

    private void processTodayData(Today today, String date, boolean save) {
        Log.e("Test", "processTodayData " + save);
        if (save) {
            mTodayRepository.saveToday(date, today);
        }
        mTodayView.showLoadingIndicator(false);
        mTodayView.showTodayData(today);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        mTodayView = null;
        MyVolley.cancleRequest(REQUEST_TAG);
    }

    private static class ResponseListener implements Response.Listener<String>, Response
            .ErrorListener, TodayDataSource.LoadTodayCallback {
        private WeakReference<TodayPresenter> weakReference;
        private String date;

        public ResponseListener(TodayPresenter presenter, String date) {
            this.date = date;
            weakReference = new WeakReference<TodayPresenter>(presenter);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(String s) {
            TodayPresenter presenter = weakReference.get();
            if (presenter != null) {
                Log.e("Test", "get today from net");
                presenter.processTodayData(s, date);
            }
        }

        @Override
        public void onTodayLoaded(Today today) {
            TodayPresenter presenter = weakReference.get();
            if (presenter != null) {
                Log.e("Test", "get today from db");
                presenter.processTodayData(today, date, false);
            }
        }

        @Override
        public void onDataNotAvailable() {
            Log.e("Test", "onDataNotAvailable");
            TodayPresenter presenter = weakReference.get();
            if (presenter != null) {
                presenter.getTodayFromNetwork(date);
            }
        }
    }
}
