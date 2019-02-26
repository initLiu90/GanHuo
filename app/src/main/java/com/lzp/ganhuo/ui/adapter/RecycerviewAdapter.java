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
        holder.txtDesc.setText(mData.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtDesc;

        public ViewHolder(CategoryRecyclerItemBinding binding) {
            super(binding.getRoot());
            txtDesc = binding.categoryListItemDesc;
        }
    }
}
