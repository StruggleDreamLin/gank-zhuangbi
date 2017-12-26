package com.practice.dreamlin.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.practice.dreamlin.gankgirl.App;
import com.practice.dreamlin.gankgirl.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dreamlin on 2017/12/26.
 */

public class Utils {

    public static final int SHARE_CODE = 0x11;

    private static void saveOrShareImage(Activity activity, String parentDir, String url, boolean shared, ShareOrSavaImp callback) {
        if (!TextUtils.isEmpty(url)) {

            TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Boolean>() {
                @Override
                protected Boolean doInBackground(Object... objects) {

                    Bitmap cache = null;
                    try {
                        cache = Glide.with(activity)
                                .asBitmap()
                                .load(url)
                                .submit()
                                .get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (cache == null)
                        return false;

                    File path = new File(sdCardPath().concat(File.separator).concat(parentDir));
                    if (!path.exists()) {
                        boolean create = path.mkdirs();
                        Logger.i("create path:" + create);
                    }
                    String filename = url.substring(url.lastIndexOf("/") + 1);
                    Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                    if (filename.endsWith("gif") || filename.endsWith("GIF")) {
                        format = Bitmap.CompressFormat.WEBP;
                    } else if (filename.endsWith("png") || filename.endsWith("PNG")) {
                        format = Bitmap.CompressFormat.PNG;
                    }
                    File image = new File(path, filename);
                    if (!image.exists()) {
                        try {
                            boolean create = image.createNewFile();
                            Logger.i("create image:" + image.getAbsolutePath() + ",res:" + create);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        FileOutputStream outputStream = new FileOutputStream(image);
                        cache.compress(format, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }

                    //插入图库
                    try {
                        MediaStore.Images.Media.insertImage(App.getInstance().getContentResolver(),
                                image.getAbsolutePath(), url, "");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //通知图库刷新
                    Intent scanner = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://".concat(image.getAbsolutePath())));
                    App.getInstance().getApplicationContext().sendBroadcast(scanner);

                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);

                    if (shared) {

                        Uri uri = Uri.fromFile(new File(sdCardPath().concat(File.separator).concat(parentDir), url));

                        if (uri == null) {
                            if (callback != null)
                                callback.callback(activity.getString(R.string.share_fail));
                            return;
                        }

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("image/*");
                        activity.startActivityForResult(shareIntent, SHARE_CODE);
                        return;
                    }

                    String msg;
                    if (aBoolean) {
                        File dir = new File(sdCardPath(), parentDir);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        msg = String.format(activity.getString(R.string.save_success), dir.getAbsolutePath());
                    } else {
                        msg = App.getInstance().getString(R.string.save_fail);
                    }
                    if (callback != null)
                        callback.callback(msg);
                }
            });
        }
    }

    public static void saveImage(Activity activity, String parentDir, String url, ShareOrSavaImp callback) {
        saveOrShareImage(activity, parentDir, url, false, callback);
    }

    public static void shareImage(Activity activity, String parentDir, String url, ShareOrSavaImp callback) {
        saveOrShareImage(activity, parentDir, url, true, callback);
    }

    public static String sdCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public interface ShareOrSavaImp {
        void callback(String message);
    }

}
