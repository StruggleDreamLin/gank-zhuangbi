package com.practice.dreamlin.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * Created by dreamlin on 2017/12/19.
 */

public abstract class BaseActivity<P extends BasePresenter> extends Activity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        this.initPresenter();
    }

    protected abstract @LayoutRes int getLayout();

    protected abstract void initPresenter();



}
