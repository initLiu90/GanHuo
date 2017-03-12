package com.example.lzp.ganhuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.fragment.BaseItem;
import com.example.lzp.ganhuo.fragment.TitleItem;
import com.example.lzp.ganhuo.fragment.today.EmptyItem;
import com.example.lzp.ganhuo.fragment.today.Today;
import com.example.lzp.ganhuo.fragment.today.TodayInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzp on 2017/3/9.
 */

public class TodayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ANDROID = 1;
    private static final int TYPE_IOS = 2;
    private static final int TYPE_WEB = 3;
    private static final int TYPE_RES = 4;
    private static final int TYPE_FULI = 5;
    private static final int TYPE_VIDEO = 6;
    private static final int TYPE_XIA = 7;

    private Context mContext;
    private List<BaseItem> mData;
    private Today mToday;
    private TodayInterface mInterface;

    public TodayRecyclerViewAdapter(Context context, TodayInterface todayInterface) {
        this.mContext = context;
        this.mInterface = todayInterface;
        mData = new ArrayList<>();
    }

    public void setData(Today data) {
        mToday = data;
        mData.clear();
        boolean isEmpty = true;
        Today.Results results = mToday.getResults();
        if (results.getFulis().size() != 0) {
            mData.addAll(data.getResults().getFulis());
        }
        if (results.getAndroids().size() != 0) {
            mData.add(new TitleItem("Android"));
            mData.addAll(data.getResults().getAndroids());
        }

        if (results.getIoss().size() != 0) {
            mData.add(new TitleItem("IOS"));
            mData.addAll(data.getResults().getIoss());
        }

        if (results.getWebs().size() != 0) {
            mData.add(new TitleItem("前端"));
            mData.addAll(data.getResults().getWebs());
        }

        if (results.getResources().size() != 0) {
            mData.add(new TitleItem("拓展资源"));
            mData.addAll(data.getResults().getResources());
        }

        if (results.getVideos().size() != 0) {
            mData.add(new TitleItem("视频"));
            mData.addAll(data.getResults().getVideos());
        }

        if (results.getXias().size() != 0) {
            mData.add(new TitleItem("瞎推荐"));
            mData.addAll(data.getResults().getXias());
        }

        if (mData.isEmpty()) {
            mData.add(new EmptyItem());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        BaseItem item = mData.get(position);
        if (item instanceof TitleItem) {
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
        } else if (item instanceof EmptyItem) {
            return TYPE_EMPTY;
        }
        return TYPE_TITLE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        switch (viewType) {
            case TYPE_EMPTY:
                view = LayoutInflater.from(mContext).inflate(R.layout.adapter_item_empty, parent, false);
                holder = new EmptyHodler(view);
                break;
            case TYPE_TITLE:
                view = LayoutInflater.from(mContext).inflate(R.layout.today_adapter_item_title, parent, false);
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
        if (holder instanceof EmptyHodler) {
            ((EmptyHodler) holder).txtEmpty.setText(R.string.empty_content);
        } else if (holder instanceof TitleHolder) {
            TitleItem title = (TitleItem) mData.get(position);
            ((TitleHolder) holder).txtTitle.setText(title.getName());
        } else if (holder instanceof PictureHolder) {
            ImageView image = ((PictureHolder) holder).imgPic;
            Today.Results.Item item = (Today.Results.Item) mData.get(position);

            String url = item.getUrl();
            Glide.with(mContext).load(url).override(800, 800).into(image);
        } else if (holder instanceof ItemeHolder) {
            ImageView image = ((ItemeHolder) holder).imgPic;
            TextView des = ((ItemeHolder) holder).txtDes;
            TextView auth = ((ItemeHolder) holder).txtAuth;

            Today.Results.Item item = (Today.Results.Item) mData.get(position);
            image.setVisibility(View.GONE);
            des.setVisibility(View.VISIBLE);
            auth.setVisibility(View.VISIBLE);
            des.setText(". " + item.getDesc());
            des.setOnClickListener(new TodayItemOnclickListener(position));
            auth.setText(item.getWho().equals("") ? "None" : item.getWho());
            String url = item.getImage();
            if (!TextUtils.isEmpty(url)) {
                image.setVisibility(View.VISIBLE);
                image.setOnClickListener(new TodayItemOnclickListener(position));
                Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).override(800, 800).into(image);
            }
        }
    }

    private class TodayItemOnclickListener implements View.OnClickListener {
        private int position;

        public TodayItemOnclickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (v instanceof TextView) {//des click
                Today.Results.Item item = (Today.Results.Item) mData.get(position);
                if (item != null) {
                    String contentUrl = item.getUrl();
                    if (mInterface != null) {
                        mInterface.showContent(contentUrl);
                    }
                }
            } else if (v instanceof ImageView) {//Image click
                Today.Results.Item item = (Today.Results.Item) mData.get(position);
                if (item != null) {
                    String imageUrl = item.getImage();
                    if (mInterface != null) {
                        mInterface.showImage(imageUrl);
                    }
                }
            }
        }
    }

    class EmptyHodler extends RecyclerView.ViewHolder {
        public TextView txtEmpty;

        public EmptyHodler(View itemView) {
            super(itemView);
            txtEmpty = (TextView) itemView;
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
