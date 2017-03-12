package com.example.lzp.ganhuo.fragment.today;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lzp.ganhuo.net.MyVolley;
import com.example.lzp.ganhuo.util.JsonParser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by SKJP on 2017/3/9.
 */

public class TodayPresenter implements TodayContract.Presenter {
    private static final String TODAY_URL = "http://gank.io/api/day/";
    private static final String REQUEST_TAG = "today";
    private TodayContract.View mTodayView;
    private ResponseListener mListener;

    public TodayPresenter(TodayContract.View todayView) {
        mTodayView = todayView;
        mListener = new ResponseListener(this);
    }

    /**
     * @param date 2017/08/21
     */
    @Override
    public void requestTodayData(String date) {
        mTodayView.showLoadingIndicator(true);
        String url = TODAY_URL + date;

        MyVolley.request(url, REQUEST_TAG,mListener,mListener);
    }

    @Override
    public void cancleRequest() {
        MyVolley.cancleRequest(REQUEST_TAG);
    }

    private void processTodayData(String s) {
        Today today = JsonParser.parse2Today(s);
        mTodayView.showLoadingIndicator(false);
        mTodayView.showTodayData(today);
    }

    @Override
    public void start() {

    }

    private static class ResponseListener implements Response.Listener<String>, Response.ErrorListener {
        private WeakReference<TodayPresenter> weakReference;

        public ResponseListener(TodayPresenter presenter) {
            weakReference = new WeakReference<TodayPresenter>(presenter);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }

        @Override
        public void onResponse(String s) {
            TodayPresenter presenter = weakReference.get();
            if (presenter != null) {
                presenter.processTodayData(s);
            }
        }
    }
}
