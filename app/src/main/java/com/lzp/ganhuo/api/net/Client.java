package com.lzp.ganhuo.api.net;

import com.lzp.ganhuo.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Client {
    private static Client sClient;
    private Retrofit mRetrofit;

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
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://square.github.io/retrofit/")
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
