package com.lzp.ganhuo.api.net;

import android.util.Log;

import com.lzp.ganhuo.App;
import com.lzp.ganhuo.api.ApiService;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Client {
    private static Client sClient;
    private Retrofit mRetrofit;
    private OkHttpClient mOkhttpClient;

    private Client() {
    }

    public static Client getClient() {
        if (sClient == null) {
            synchronized (Client.class) {
                if (sClient == null) {
                    sClient = new Client();
                }
            }
        }
        return sClient;
    }

    public ApiService getApiService() {
        if (mRetrofit == null) {
            initRetrofit();
        }
        return mRetrofit.create(ApiService.class);
    }

    private void initRetrofit() {
        initOkhttp();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://square.github.io/retrofit/")
                .client(mOkhttpClient)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void initOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            Class.forName("com.readystatesoftware.chuck.ChuckInterceptor");
            builder.addInterceptor(new ChuckInterceptor(App.getApp().getBaseContext()));
        } catch (ClassNotFoundException e) {
            Log.e("Test", "cannot find ChuckInterceptor");
        }
        mOkhttpClient = builder.build();
    }
}
