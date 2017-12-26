package com.practice.dreamlin.zhuangbi.fragments;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.practice.dreamlin.base.TabFragment;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.zhuangbi.adapter.ZbAdapter;
import com.practice.dreamlin.zhuangbi.mvp.bean.ZhuangBiBean;
import com.practice.dreamlin.zhuangbi.mvp.p.ZbPresenter;
import com.practice.dreamlin.zhuangbi.mvp.v.IZbView;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/25.
 */

public class ZbCommonFragment extends TabFragment<ZbAdapter, ZbPresenter> implements IZbView, ZbAdapter.ItemClicked {

    private String mKeywords;

    @Override
    protected int getLayout() {
        return R.layout.swipe_refresh_layout;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ZbPresenter(this, getActivity());
        if (isUIVisible) {
            loadData();
        }
    }

    @Override
    protected void initRecycleView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
                        >= mAdapter.getItemCount() - 4;
                if (isBottom && !mSwipeRefreshLayout.isRefreshing()) {
                    onLoadmore();
                }
            }
        });
        mAdapter = new ZbAdapter(getActivity());
        mAdapter.setItemListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void loadData() {
        mPresenter.refreshData(mKeywords);
    }

    @Override
    public void onLoadmore() {
        mSwipeRefreshLayout.setEnabled(true);
        super.onLoadmore();
        mPresenter.loadMoreData(mKeywords, mAdapter.getItemCount());
    }

    @Override
    public void noMoreData(String nomore) {
        super.noMoreData(nomore);
//        super.hideRefreshWithDisabled();
        if (mSwipeRefreshLayout.isEnabled()) {
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    public void setKeywords(String keywords) {
        mKeywords = keywords;
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void appendMoreData(List<ZhuangBiBean> biBeans) {
        mAdapter.appendAll(biBeans);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void addAllData(List<ZhuangBiBean> biBeans) {
        mAdapter.addAll(biBeans);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onItemClick(ZhuangBiBean bean, int position) {
        Snackbar.make(mRecyclerView,
                getString(R.string.share_or_save), Snackbar.LENGTH_LONG)
                .setAction(R.string.action_save, v -> mPresenter.saveImage(bean.url))
                .show();
    }

}
