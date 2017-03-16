package com.example.lzp.ganhuo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lzp.ganhuo.R;

/**
 * Created by lzp on 2017/3/14.
 */

public class ImageGalleryActivity extends BaseActivity {
    public static final String KEY_CUR_URL = "cur_url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagegallery);
        String url = getIntent().getStringExtra(KEY_CUR_URL);//当前显示的图片的url

    }
}
