package com.example.lzp.ganhuo.activity.ImageGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lzp.ganhuo.R;
import com.example.lzp.ganhuo.activity.BaseActivity;
import com.example.lzp.ganhuo.app.BaseApplication;
import com.example.lzp.ganhuo.data.today.TodayLocalDataSource;
import com.example.lzp.ganhuo.data.today.TodayRepository;

/**
 * Created by lzp on 2017/3/14.
 */

public class ImageGalleryActivity extends BaseActivity implements ImageGalleryContract.View {
    public static final String KEY_CUR_URL = "cur_url";
    public static final String KEY_CUR_DATE = "date";

    private ImageGalleryContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagegallery);
        String url = getIntent().getStringExtra(KEY_CUR_URL);//当前显示的图片的url
        String date = getIntent().getStringExtra(KEY_CUR_DATE);
        mPresenter = new ImageGalleryPresenter(this, TodayRepository.getInstance(TodayLocalDataSource.getInstance(BaseApplication.sApplication)));
        mPresenter.getImages(date);
    }

    @Override
    public void showImages() {

    }
}
