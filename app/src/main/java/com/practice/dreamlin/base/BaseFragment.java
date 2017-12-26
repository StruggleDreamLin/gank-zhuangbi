package com.practice.dreamlin.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by dreamlin on 2017/12/19.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected Disposable disposable;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);//attchToRoot means ?
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void initPresenter();


}
