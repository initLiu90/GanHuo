package com.lzp.ganhuo.ui.binding;

import com.lzp.ganhuo.ui.adapter.BaseRecyclerViewAdapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public final class BindingAdapters {
    @BindingAdapter(value = "refreshing")
    public static void isRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRefresing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(isRefresing);
        }
    }

    @BindingAdapter(value = "onRefresh")
    public static void setOnRefreshListener(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(listener);
        }
    }

    @BindingAdapter(value = "showMore")
    public static void showMore(RecyclerView recyclerView, boolean show) {
        if (recyclerView != null) {
            if (recyclerView.getAdapter() != null && recyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
                BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();
                adapter.showMore(show);
            }
        }
    }
}
