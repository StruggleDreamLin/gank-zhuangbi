package com.practice.dreamlin.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.practice.dreamlin.base.BaseAppCompatActivity;
import com.practice.dreamlin.gankgirl.R;

import butterknife.BindView;

/**
 * Created by dreamlin on 2017/12/23.
 */

public class WebActivity extends BaseAppCompatActivity {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_URL = "url";

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        loadRequest(url);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebViewClient(new CstWebClick());
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mWebView.reload();
            }
        });

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    public static void startWebView(Activity activity,
                                    final String title, final String url) {
        Intent intent = new Intent();
        intent.setClass(activity, WebActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);
    }

    private void loadRequest(String url) {
        if (!TextUtils.isEmpty(url))
            mWebView.loadUrl(url);
    }

    protected void showRefresh() {
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
    }

    protected void hideRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    protected void onError(CharSequence error) {
        Snackbar.make(mWebView, error, Snackbar.LENGTH_SHORT).show();
    }

    private class CstWebClick extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showRefresh();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideRefresh();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                onError(error.getDescription());
            } else {
                onError(error.toString());
            }
        }
    }

}
