package com.lzp.ganhuo.model;

import com.lzp.ganhuo.model.bean.CategoryItem;
import com.lzp.ganhuo.api.net.Client;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private static Repository sInstance;

    private Repository() {
    }

    public static Repository getInstance() {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository();
                }
            }
        }
        return sInstance;
    }

    public Observable<CategoryItem> categoryItem(String category, int count, int page) {
        return Client.getClient().getApiService()
                .categoryItem(category, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
