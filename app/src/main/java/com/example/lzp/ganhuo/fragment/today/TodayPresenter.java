package com.example.lzp.ganhuo.fragment.today;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lzp.ganhuo.data.today.TodayRepository;
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
    public void requestTodayData(String date) {
        mTodayView.showLoadingIndicator(true);
        String url = TODAY_URL + date;
        ResponseListener listener = new ResponseListener(this, date);
        MyVolley.request(url, REQUEST_TAG, listener, listener);
    }

    @Override
    public void cancleRequest() {
        MyVolley.cancleRequest(REQUEST_TAG);
    }

    private void processTodayData(String s, String date) {
        Today today = JsonParser.parse2Today(s);
        mTodayRepository.saveToday(date, today);
        mTodayView.showLoadingIndicator(false);
        mTodayView.showTodayData(today);
    }

    @Override
    public void start() {

    }

    private static class ResponseListener implements Response.Listener<String>, Response
            .ErrorListener {
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
                presenter.processTodayData(s, date);
            }
        }
    }
}
