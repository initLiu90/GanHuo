package com.example.lzp.ganhuo.data.today;

import com.example.lzp.ganhuo.fragment.today.Today;

import java.util.List;

/**
 * Created by SKJP on 2017/3/16.
 */

public interface TodayDataSource {

    interface LoadTodayCallback {
        void onTodayLoaded(Today today);

        void onDataNotAvailable();
    }

    interface LoadTodayImageCallback {
        void onTodayImageLoaded(List<String> images);
    }

    void getToday(String date, LoadTodayCallback callback);

    void saveToday(String date, Today today);

    void getTodayImage(String date,LoadTodayImageCallback callback);

}
