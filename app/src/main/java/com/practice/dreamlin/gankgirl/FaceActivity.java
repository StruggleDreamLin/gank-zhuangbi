package com.practice.dreamlin.gankgirl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.practice.dreamlin.base.BaseAppCompatActivity;
import com.practice.dreamlin.gankgirl.mvp.p.GankFacePresenter;
import com.practice.dreamlin.gankgirl.mvp.p.GankPresenter;
import com.practice.dreamlin.gankgirl.mvp.v.IFaceView;
import com.practice.dreamlin.util.Logger;

import butterknife.BindView;

/**
 * Created by dreamlin on 2017/12/23.
 */

public class FaceActivity extends BaseAppCompatActivity<GankFacePresenter> implements IFaceView {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_URL = "url";

    private String url = "";

    @BindView(R.id.girl_face)
    PhotoView mPhotoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(mPhotoView);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gank_face;
    }

    @Override
    protected int getMenu() {
        return R.menu.gank_girl_face;
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GankFacePresenter(this, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:
                mPresenter.saveImage(url);
                return true;
            case R.id.action_share:
                mPresenter.shareImage(url);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showTips(String tips) {
        Snackbar.make(getWindow().getDecorView(), tips, Snackbar.LENGTH_SHORT)
                .show();
    }

    public static void startFace(final Activity activity, final String url, final String title) {
        Intent intent = new Intent();
        intent.setClass(activity, FaceActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GankFacePresenter.SHARE_CODE) {
            Logger.i("resultCode:" + resultCode);

        }

    }
}
