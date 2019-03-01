package com.lzp.ganhuo.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private boolean mShowMore = false;

    public abstract void setData(T data);

    public abstract void addData(T data);

    public void showMore(boolean show) {
        mShowMore = show;
        notifyItemInserted(getItemCount());
    }

    public boolean isShowMore() {
        return mShowMore;
    }
}
