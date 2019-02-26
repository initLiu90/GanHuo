package com.lzp.ganhuo.viewmodel;

import com.lzp.ganhuo.model.bean.CategoryItem;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityMainViewModel extends ViewModel {
    private final String[] categorys = {"Android", "iOS", "前端", "拓展资源", "福利"};
    private final MutableLiveData<Map<Integer, CategoryItem>> mData;

    public ActivityMainViewModel() {
        mData = new MutableLiveData<>();
        mData.setValue(null);
    }

    public String[] getCategorys() {
        return categorys;
    }
}
