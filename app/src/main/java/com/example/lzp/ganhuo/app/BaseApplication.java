package com.example.lzp.ganhuo.app;

import android.app.Application;

/**
 * Created by lzp on 2017/3/8.
 */

public class BaseApplication extends Application {
    public static BaseApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
