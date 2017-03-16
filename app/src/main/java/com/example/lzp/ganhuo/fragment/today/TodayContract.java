package com.example.lzp.ganhuo.fragment.today;

import com.example.lzp.ganhuo.fragment.BasePresenter;
import com.example.lzp.ganhuo.fragment.BaseView;

/**
 * Created by SKJP on 2017/3/9.
 */

public interface TodayContract {
    interface Presenter extends BasePresenter {
        void requestTodayData(String date);
    }

    interface View extends BaseView<Presenter> {
        void showTodayData(Today data);

        void showLoadingIndicator(boolean active);
    }
}
