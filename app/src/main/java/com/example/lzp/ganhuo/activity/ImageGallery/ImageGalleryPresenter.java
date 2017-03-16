package com.example.lzp.ganhuo.activity.ImageGallery;

import android.util.Log;

import com.example.lzp.ganhuo.data.today.TodayDataSource;
import com.example.lzp.ganhuo.data.today.TodayRepository;
import com.example.lzp.ganhuo.fragment.BasePresenter;

import java.util.List;

/**
 * Created by lzp on 2017/3/17.
 */

public class ImageGalleryPresenter implements ImageGalleryContract.Presenter {
    private ImageGalleryContract.View mImageGalleryView;
    private TodayRepository mRepository;

    public ImageGalleryPresenter(ImageGalleryContract.View view, TodayRepository repository) {
        mImageGalleryView = view;
        mRepository = repository;
    }

    @Override
    public void getImages(String date) {
        mRepository.getTodayImage(date, new TodayDataSource.LoadTodayImageCallback() {
            @Override
            public void onTodayImageLoaded(List<String> images) {
                for (String str : images) {
                    Log.e("Test", str);
                }
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
