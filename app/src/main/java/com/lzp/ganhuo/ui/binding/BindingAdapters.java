package com.lzp.ganhuo.ui.binding;

import com.lzp.ganhuo.model.bean.CategoryItem;
import com.lzp.ganhuo.ui.adapter.BaseRecyclerViewAdapter;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public final class BindingAdapters {
    @BindingAdapter(value = "items")
    public static void recyclerViewItems(RecyclerView recyclerView, CategoryItem categoryItem) {
        if (categoryItem != null) {
            List<CategoryItem.Item> items = categoryItem.getResults();
            if (items != null && items.size() > 0) {
                if (recyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
                    ((BaseRecyclerViewAdapter) recyclerView.getAdapter()).addData(items);
                }
            }
        }
    }
}
