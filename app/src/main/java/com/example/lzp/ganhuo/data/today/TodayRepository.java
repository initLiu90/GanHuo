package com.example.lzp.ganhuo.data.today;

import android.content.Context;
import android.util.Log;

import com.example.lzp.ganhuo.fragment.today.Today;

/**
 * Created by SKJP on 2017/3/16.
 */

public class TodayRepository implements TodayDataSource {
    private static TodayRepository INSTANCE = null;

    private TodayDataSource mTodayLoaclDataSource;

    public static TodayRepository getInstance(TodayDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TodayRepository(localDataSource);
        }
        return INSTANCE;
    }

    private TodayRepository(TodayDataSource todayLocalDataSource) {
        mTodayLoaclDataSource = todayLocalDataSource;
    }

    @Override
    public void getToday(String date, LoadTodayCallback callback) {
        mTodayLoaclDataSource.getToday(date, callback);
    }

    @Override
    public void saveToday(String date, Today today) {
        mTodayLoaclDataSource.saveToday(date, today);
    }

    @Override
    public void getTodayImage(String date, LoadTodayImageCallback callback) {
        mTodayLoaclDataSource.getTodayImage(date, callback);
    }
}
