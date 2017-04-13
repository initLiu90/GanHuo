package com.example.lzp.ganhuo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lzp.ganhuo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzp on 2017/4/9.
 */

public class ImageGalleryAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;

    public ImageGalleryAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<String> data) {
        mData.clear();
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemPosition(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            return mData.indexOf(imageUrl);
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gallery_adapter_item, parent, false);
            ImageGalleryItemHolder holder = new ImageGalleryItemHolder();
            holder.mImage = (ImageView) convertView.findViewById(R.id.gallery_adapter_item_image);
            convertView.setTag(holder);
        }
        ImageGalleryItemHolder holder = (ImageGalleryItemHolder) convertView.getTag();

        String url = mData.get(position);
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).override(800, 800).into(holder.mImage);
        return convertView;
    }

    static class ImageGalleryItemHolder {
        ImageView mImage;
    }
}
