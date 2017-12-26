package com.practice.dreamlin.refresh.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.practice.dreamlin.base.BaseFragment;
import com.practice.dreamlin.base.BasePresenter;
import com.practice.dreamlin.gankgirl.PagerWatcher;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.refresh.IRefreshView;
import com.practice.dreamlin.util.Logger;

import butterknife.BindView;

/**
 * Created by dreamlin on 2017/12/20.
 */

public abstract class RefreshFragment<A extends RecyclerView.Adapter, P extends BasePresenter>
        extends BaseFragment<P> implements IRefreshView {

    @BindView(R.id.swipe_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    protected A mAdapter;

    public RefreshFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mSwipeRefreshLayout.setOnRefreshListener(() -> onRefresh());
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW);
        initRecycleView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (getMenu() > 0) {
            inflater.inflate(getMenu(), menu);
        } else {
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onFinish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected @MenuRes
    int getMenu() {
        return -1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    protected abstract void initRecycleView();

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout == null)
            return;
        if (!mSwipeRefreshLayout.isEnabled())
            return;
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onLoadmore() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void noMoreData(String nomore) {
        Snackbar.make(mRecyclerView, nomore, Snackbar.LENGTH_SHORT).show();
        hideRefresh();
    }

    @Override
    public void onFinish() {
        hideRefresh();
    }

    @Override
    public void onError(String error) {
        Snackbar.make(mRecyclerView, error,
                Snackbar.LENGTH_SHORT).show();
        Logger.e(error);
        hideRefresh();
    }

    protected void hideRefresh() {
        mSwipeRefreshLayout.postDelayed(() -> {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);//延迟500-1000ms以处理数据
    }

    protected void hideRefreshWithDisabled() {
        mSwipeRefreshLayout.postDelayed(() -> {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (mSwipeRefreshLayout.isEnabled())
                    mSwipeRefreshLayout.setEnabled(false);
            }
        }, 500);//延迟500-1000ms以处理数据
    }

    public void onNotify() {
        if (mRecyclerView != null)
            mRecyclerView.smoothScrollToPosition(0);
    }
}
