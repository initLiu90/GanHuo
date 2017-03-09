package com.example.lzp.ganhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.fragment.today.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzp on 2017/3/9.
 */

public class TodayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ANDROID = 1;
    private static final int TYPE_IOS = 2;
    private static final int TYPE_WEB = 3;
    private static final int TYPE_RES = 4;
    private static final int TYPE_FULI = 5;
    private static final int TYPE_VIDEO = 6;
    private static final int TYPE_XIA = 7;

    private Context mContext;
    private List<Today.Results.Item> mData;
    private Today mToday;

    public TodayRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(Today data) {
        mToday = data;
        mData.clear();
        mData.addAll(data.getResults().getFulis());
        mData.add(new Today.Results.Title("Android"));
        mData.addAll(data.getResults().getAndroids());
        mData.add(new Today.Results.Title("IOS"));
        mData.addAll(data.getResults().getIoss());
        mData.add(new Today.Results.Title("前端"));
        mData.addAll(data.getResults().getWebs());
        mData.add(new Today.Results.Title("拓展资源"));
        mData.addAll(data.getResults().getResources());
        mData.add(new Today.Results.Title("视频"));
        mData.addAll(data.getResults().getVideos());
        mData.add(new Today.Results.Title("瞎推荐"));
        mData.addAll(data.getResults().getXias());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Today.Results.Item item = mData.get(position);
        if (item instanceof Today.Results.Title) {
            return TYPE_TITLE;
        } else if (item instanceof Today.Results.Android) {
            return TYPE_ANDROID;
        } else if (item instanceof Today.Results.Ios) {
            return TYPE_IOS;
        } else if (item instanceof Today.Results.Web) {
            return TYPE_WEB;
        } else if (item instanceof Today.Results.Video) {
            return TYPE_VIDEO;
        } else if (item instanceof Today.Results.Resource) {
            return TYPE_RES;
        } else if (item instanceof Today.Results.Fuli) {
            return TYPE_FULI;
        } else if (item instanceof Today.Results.Xia) {
            return TYPE_XIA;
        }
        return TYPE_TITLE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_TITLE:
                View view = LayoutInflater.from(mContext).inflate(R.layout.today_adapter_item_title, parent, false);
                holder = new TitleHolder(view);
                break;
            case TYPE_FULI:
                view = LayoutInflater.from(mContext).inflate(R.layout.today_adapter_item_pic, parent, false);
                holder = new PictureHolder(view);
                break;
            case TYPE_ANDROID:
            case TYPE_IOS:
            case TYPE_RES:
            case TYPE_VIDEO:
            case TYPE_XIA:
            case TYPE_WEB:
                view = LayoutInflater.from(mContext).inflate(R.layout.today_adapter_item, parent, false);
                holder = new ItemeHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            Today.Results.Title title = (Today.Results.Title) mData.get(position);
            ((TitleHolder) holder).txtTitle.setText(title.getName());
        } else if (holder instanceof PictureHolder) {
            ImageView image = ((PictureHolder) holder).imgPic;
            Today.Results.Item item = mData.get(position);

            String url = item.getUrl();
            Glide.with(mContext).load(url).override(800, 800).into(image);
        } else if (holder instanceof ItemeHolder) {
            ImageView image = ((ItemeHolder) holder).imgPic;
            TextView des = ((ItemeHolder) holder).txtDes;
            TextView auth = ((ItemeHolder) holder).txtAuth;

            Today.Results.Item item = mData.get(position);
            image.setVisibility(View.GONE);
            des.setVisibility(View.VISIBLE);
            auth.setVisibility(View.VISIBLE);
            des.setText(". " + item.getDesc());
            auth.setText(item.getWho().equals("") ? "None" : item.getWho());
            String url = item.getImage();
            if (!TextUtils.isEmpty(url)) {
                image.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(url).override(800, 800).into(image);
            }
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;

        public TitleHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView;
        }
    }

    class PictureHolder extends RecyclerView.ViewHolder {
        public ImageView imgPic;

        public PictureHolder(View itemView) {
            super(itemView);
            imgPic = (ImageView) itemView;
        }
    }

    class ItemeHolder extends RecyclerView.ViewHolder {
        public TextView txtDes;
        public TextView txtAuth;
        public ImageView imgPic;

        public ItemeHolder(View itemView) {
            super(itemView);
            txtDes = (TextView) itemView.findViewById(R.id.today_adapter_item_des);
            txtAuth = (TextView) itemView.findViewById(R.id.today_adapter_item_auth);
            imgPic = (ImageView) itemView.findViewById(R.id.today_adapter_item_image);
        }
    }
}
