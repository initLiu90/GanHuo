package com.lzp.ganhuo.viewmodel;

import com.lzp.ganhuo.api.ApiCallException;
import com.lzp.ganhuo.model.Repository;
import com.lzp.ganhuo.model.bean.CategoryItem;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentRecyclerViewmodel extends ViewModel {
    private final MutableLiveData<CategoryItem> mItem = new MutableLiveData<>();
    private final MutableLiveData<CategoryItem> mMoreItem = new MutableLiveData<>();

    private final MutableLiveData<Pair<ApiCallException.Type, String>> mError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsRefresing = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsLoadingMore = new MutableLiveData<>();

    private int mCurPage = 0;
    private String mCategory;

    public FragmentRecyclerViewmodel(String category) {
        mCategory = category;
    }

    public LiveData<CategoryItem> getItem() {
        return mItem;
    }

    public LiveData<CategoryItem> getMoreItem() {
        return mMoreItem;
    }

    public LiveData<Pair<ApiCallException.Type, String>> getError() {
        return mError;
    }

    public LiveData<Boolean> isRefresing() {
        return mIsRefresing;
    }

    public LiveData<Boolean> isLoadingMore() {
        return mIsLoadingMore;
    }

    public void lastedData() {
        mIsRefresing.setValue(true);
        mCurPage = 1;
        requestData(false);
    }

    public void loadMore() {
        mIsLoadingMore.setValue(true);
        mCurPage++;
        requestData(true);
    }

    private void requestData(boolean more) {
        Repository.getInstance()
                .categoryItem(mCategory, 10, mCurPage)
                .subscribe(item -> {
                    if (more) mMoreItem.setValue(item);
                    else mItem.setValue(item);

                    mIsRefresing.setValue(false);
                    mIsLoadingMore.setValue(false);
                }, t -> {
                    if (t instanceof ApiCallException) {
                        ApiCallException ex = (ApiCallException) t;
                        mError.setValue(new Pair<>(ex.getType(), ex.getErrorBodyJsonString()));
                    }
                });
    }
}
