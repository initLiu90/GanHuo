package com.example.lzp.ganhuo.activity.ImageGallery;

import com.example.lzp.ganhuo.fragment.BasePresenter;
import com.example.lzp.ganhuo.fragment.BaseView;

/**
 * Created by lzp on 2017/3/17.
 */

public interface ImageGalleryContract {
    interface Presenter extends BasePresenter {
        void getImages(String date);
    }

    interface View extends BaseView<Presenter> {
        void showImages();
    }
}
