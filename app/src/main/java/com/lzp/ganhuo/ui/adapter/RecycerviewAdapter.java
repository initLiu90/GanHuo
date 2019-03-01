package com.lzp.ganhuo.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzp.ganhuo.R;
import com.lzp.ganhuo.databinding.CategoryRecyclerItemBinding;
import com.lzp.ganhuo.model.bean.CategoryItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecycerviewAdapter extends BaseRecyclerViewAdapter<List<CategoryItem.Item>, RecycerviewAdapter.ViewHolder> {
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int ITEM_TYPE_MORE = 2;

    private List<CategoryItem.Item> mData;

    @Override
    public void setData(List<CategoryItem.Item> data) {
        if (data == null || data.size() == 0) return;
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<CategoryItem.Item> data) {
        if (data == null || data.size() == 0) return;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int pos = mData.size();
        mData.addAll(data);
        notifyItemInserted(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryRecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_recycler_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setItem(mData.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        } else {
            if (isShowMore()) {
                return mData.size() + 1;
            } else {
                return mData.size();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowMore()) {
            if (position == mData.size()) {
                return ITEM_TYPE_MORE;
            }
        }
        return ITEM_TYPE_NORMAL;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CategoryRecyclerItemBinding binding;

        public ViewHolder(CategoryRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
