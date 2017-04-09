package com.example.lzp.ganhuo.activity;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.lzp.ganhuo.R;

import java.lang.ref.WeakReference;

/**
 * Created by lzp on 2017/3/12.
 */

public class BrowserActivity extends BaseActivity implements Handler.Callback {
    public static final String KEY_URL = "url";

    private WebView mWebView;
    private WebViewClient mClient;
    private WebChromeClient mChromeClient;
    private String mUrl;
    private ProgressBar mProgess;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHander;
    private Handler mFrontHandler;

    private static final int MSG_START_PROGRESS = 100;
    private static final int MSG_UPDATE_PROGRESS = 101;
    private static final int MSG_SET_PROGRESS = 102;
    private static final int MSG_STOP_PROGRESS = 103;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        initWebView();
        mBackgroundThread = new HandlerThread("background");
        mBackgroundThread.start();
        mBackgroundHander = new Handler(mBackgroundThread.getLooper(), this);
        mFrontHandler = new Handler(Looper.getMainLooper(), this);
    }

    private void initWebView() {
        mProgess = (ProgressBar) findViewById(R.id.activity_browser_progress);
        mWebView = (WebView) findViewById(R.id.activity_browser_webview);
        mClient = new MyWebViewClient(this);
        mChromeClient = new MyChromeClient(this);
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChromeClient);
        String url = getIntent().getStringExtra(KEY_URL);
        if (!TextUtils.isEmpty(url)) {
            mUrl = url;
            mWebView.loadUrl(url);
        }
    }

    private static class MyChromeClient extends WebChromeClient {
        private WeakReference<BrowserActivity> weakReference;

        public MyChromeClient(BrowserActivity activity) {
            weakReference = new WeakReference<BrowserActivity>(activity);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            BrowserActivity activity = weakReference.get();
            if (activity != null) {
                activity.setTitle(title);
            }
        }
    }

    private static class MyWebViewClient extends WebViewClient {
        private WeakReference<BrowserActivity> weakReference;

        public MyWebViewClient(BrowserActivity activity) {
            weakReference = new WeakReference<BrowserActivity>(activity);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            BrowserActivity activity = weakReference.get();
            if (activity != null) {
                activity.updateUI(MSG_START_PROGRESS);
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            BrowserActivity activity = weakReference.get();
            if (activity != null) {
                activity.updateUI(MSG_STOP_PROGRESS);
            }
        }
    }

    private void updateUI(int msg) {
        if (mFrontHandler != null) {
            mFrontHandler.sendEmptyMessage(msg);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        switch (what) {
            case MSG_START_PROGRESS:
                mProgess.setVisibility(View.VISIBLE);
                mProgess.setMax(10000);
                mProgess.setProgress(0);
                mBackgroundHander.sendEmptyMessage(MSG_UPDATE_PROGRESS);
                break;
            case MSG_STOP_PROGRESS:
                mProgess.setProgress(10000);
                mBackgroundHander.removeMessages(MSG_UPDATE_PROGRESS);

                AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(500);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mProgess.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mProgess.startAnimation(animation);
                break;
            case MSG_UPDATE_PROGRESS:
                mFrontHandler.sendEmptyMessage(MSG_SET_PROGRESS);
                mBackgroundHander.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 300);
                break;
            case MSG_SET_PROGRESS:
                int progress = mProgess.getProgress();
                int max = mProgess.getMax();
                if (progress < max) {
                    progress += 1000;
                }
                if (progress > max) {
                    progress = max - 1000;
                }
                mProgess.setProgress(progress);
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.removeAllViews();

        mProgess.clearAnimation();

        mBackgroundHander.removeCallbacksAndMessages(null);
        mFrontHandler.removeCallbacksAndMessages(null);

        mBackgroundHander = null;
        mFrontHandler = null;
        mChromeClient = null;
        mClient = null;
        mProgess = null;
        mWebView = null;
    }
}
