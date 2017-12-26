package com.practice.dreamlin.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.dreamlin.refresh.fragment.RefreshFragment;

/**
 * Created by dreamlin on 2017/12/26.
 */

public abstract class TabFragment<A extends RecyclerView.Adapter, P extends BasePresenter> extends RefreshFragment<A, P> {

    protected boolean isUIVisible = false;
    protected boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isCreated = true;
//        mSwipeRefreshLayout.setOnRefreshListener(() -> lazyLoad());
        return view;
    }

    private void lazyLoad() {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isRefreshing()) {
                return;
            } else {
                mSwipeRefreshLayout.setRefreshing(true);
                mSwipeRefreshLayout.postDelayed(() ->
                        mSwipeRefreshLayout.setRefreshing(false), 1000);
            }
        } else {
            return;
        }
//        Logger.e("isLoad:" + isLoad);
        if (!isCreated || !isUIVisible) {
            return;
        }
        loadData();
    }

    protected abstract void loadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreated = false;
    }


}
