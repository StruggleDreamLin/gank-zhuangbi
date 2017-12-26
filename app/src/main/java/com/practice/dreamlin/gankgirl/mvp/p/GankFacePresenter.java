package com.practice.dreamlin.gankgirl.mvp.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.practice.dreamlin.base.BasePresenter;
import com.practice.dreamlin.gankgirl.App;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.gankgirl.mvp.v.IFaceView;
import com.practice.dreamlin.util.Logger;
import com.practice.dreamlin.util.TaskUtils;
import com.practice.dreamlin.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dreamlin on 2017/12/23.
 */

public class GankFacePresenter extends BasePresenter<IFaceView> {

    private AppCompatActivity mContext;
    public static final int SHARE_CODE = 0x11;

    public GankFacePresenter(IFaceView faceView, AppCompatActivity context) {
        mView = faceView;
        mContext = context;
    }

    /**
     * Android 6.0及以上WRITE_EXTXTERNAL 权限需要动态申请
     *
     * @param url
     */

    public void saveImage(String url) {
        Utils.saveImage(mContext, "Gankgirl", url, message -> mView.showTips(message));
    }

    public void shareImage(String url) {
        Utils.shareImage(mContext, "Gankgirl", url, message -> mView.showTips(message));
    }

}
