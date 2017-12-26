package com.practice.dreamlin.gankgirl.fragments;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.practice.dreamlin.base.TabFragment;
import com.practice.dreamlin.gankgirl.FaceActivity;
import com.practice.dreamlin.gankgirl.PagerWatcher;
import com.practice.dreamlin.gankgirl.adapter.GankCommonAdapter;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.refresh.fragment.RefreshFragment;
import com.practice.dreamlin.gankgirl.mvp.bean.GankEnity;
import com.practice.dreamlin.gankgirl.mvp.p.GankPresenter;
import com.practice.dreamlin.gankgirl.mvp.v.IGankView;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class GirlsFragment extends TabFragment<GankCommonAdapter, GankPresenter>
        implements IGankView, GankCommonAdapter.ItemClicked {

    public GirlsFragment() {
        PagerWatcher.addWatcher(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_girls;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GankPresenter(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        disposable = mPresenter.refreshData("福利");
    }

    @Override
    protected void loadData() {
        onRefresh();
    }

    @Override
    public void onLoadmore() {
        super.onLoadmore();
        disposable = mPresenter.loadMoreData("福利", mAdapter.getItemCount());
    }

    @Override
    public void showError(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void appendMoreData(List<GankEnity> list) {
        mAdapter.appendAll(list);
    }

    @Override
    public void addAllData(List<GankEnity> list) {
        mAdapter.addAll(list);
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
        mAdapter = new GankCommonAdapter(this.getActivity());
        mAdapter.setItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(GankEnity bean, int position) {
        FaceActivity.startFace(getActivity(), bean.url, bean.desc);
    }

}
