package com.example.lzp.ganhuo.activity.ImageGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Gallery;
import android.widget.TextView;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.activity.BaseActivity;
import com.example.lzp.ganhuo.app.BaseApplication;
import com.example.lzp.ganhuo.data.today.TodayLocalDataSource;
import com.example.lzp.ganhuo.data.today.TodayRepository;

import java.util.List;

/**
 * Created by lzp on 2017/3/14.
 */

public class ImageGalleryActivity extends BaseActivity implements ImageGalleryContract.View {
    public static final String KEY_CUR_URL = "cur_url";
    public static final String KEY_CUR_DATE = "date";

    private ImageGalleryContract.Presenter mPresenter;
    private Gallery mGallery;
    private TextView mTxtPostion;
    private String mDefaultUrl;
    private String mDefaultDate;
    private ImageGalleryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagegallery);
        initView();
        initData();
    }

    private void initView() {
        mGallery = (Gallery) findViewById(R.id.activity_imagegallery_gallery);
        mTxtPostion = (TextView) findViewById(R.id.activity_imagegallery_position);
    }

    private void initData() {
        mAdapter = new ImageGalleryAdapter(this);
        mGallery.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDefaultUrl = getIntent().getStringExtra(KEY_CUR_URL);//当前显示的图片的url
        mDefaultDate = getIntent().getStringExtra(KEY_CUR_DATE);
        mPresenter = new ImageGalleryPresenter(this, TodayRepository.getInstance(TodayLocalDataSource.getInstance(BaseApplication.sApplication)));
        mPresenter.getImages(mDefaultDate);
    }

    @Override
    public void showImages(List<String> imageUrls) {
        mAdapter.setData(imageUrls);
    }
}
