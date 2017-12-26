package com.practice.dreamlin.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.practice.dreamlin.util.Logger;

import butterknife.ButterKnife;

/**
 * Created by dreamlin on 2017/12/19.
 */

public abstract class BaseAppCompatActivity<P extends BasePresenter> extends AppCompatActivity {

    protected P mPresenter;
    protected final int PERSSION_CODE = 0x010;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initViews();
        addListeners();
        this.initPresenter();
    }

    protected abstract @LayoutRes
    int getLayout();

    protected int getMenu() {
        return -0x1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (getMenu() < 0)
            return super.onCreateOptionsMenu(menu);
        else {
            getMenuInflater().inflate(getMenu(), menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void addListeners() {

    }

    protected abstract void initViews();

    protected abstract void initPresenter();

    protected void initDatas() {

    }

    protected void requestPermissions(String[] permissions) {

        if (permissions.length < 1) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, permissions[0])
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permissions[0])) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {

                        startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName())));
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, PERSSION_CODE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
