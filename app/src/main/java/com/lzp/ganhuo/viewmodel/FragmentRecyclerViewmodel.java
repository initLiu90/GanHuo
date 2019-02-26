package com.lzp.ganhuo.viewmodel;

import com.lzp.ganhuo.api.ApiCallException;
import com.lzp.ganhuo.model.Repository;
import com.lzp.ganhuo.model.bean.CategoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.core.util.Pair;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FragmentRecyclerViewmodel extends ViewModel {
    private final MutableLiveData<CategoryItem> mItem = new MutableLiveData<>();
    private MutableLiveData<Pair<ApiCallException.Type, String>> mError = new MutableLiveData<>();


    public void requestCategoryItem(String category) {
        Repository.getInstance()
                .categoryItem(category, 10, 1)
                .subscribe(item -> {
                    mItem.setValue(item);
                }, t -> {
                    if (t instanceof ApiCallException) {
                        ApiCallException ex = (ApiCallException) t;
                        mError.setValue(new Pair<>(ex.getType(), ex.getErrorBodyJsonString()));
                    }
                });
    }

    public LiveData<CategoryItem> getItem() {
        return mItem;
    }

    public LiveData<Pair<ApiCallException.Type, String>> getError() {
        return mError;
    }
}
