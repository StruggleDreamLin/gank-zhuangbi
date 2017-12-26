package com.practice.dreamlin.gankgirl.fragments;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.practice.dreamlin.base.TabFragment;
import com.practice.dreamlin.gankgirl.PagerWatcher;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.gankgirl.adapter.GankCommonAdapter;
import com.practice.dreamlin.gankgirl.mvp.bean.GankEnity;
import com.practice.dreamlin.gankgirl.mvp.p.GankPresenter;
import com.practice.dreamlin.gankgirl.mvp.v.IGankView;
import com.practice.dreamlin.refresh.fragment.RefreshFragment;
import com.practice.dreamlin.web.WebActivity;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class GankCommonFragment extends TabFragment<GankCommonAdapter, GankPresenter>
        implements IGankView, GankCommonAdapter.ItemClicked {

    String mCategory;

    public GankCommonFragment() {
        PagerWatcher.addWatcher(this);
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_common_gank;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GankPresenter(this);
        if (isUIVisible) {
            loadData();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        disposable = mPresenter.refreshData(mCategory);
    }

    @Override
    public void onLoadmore() {
        super.onLoadmore();
        disposable = mPresenter.loadMoreData(mCategory, mAdapter.getItemCount());
    }

    @Override
    protected void loadData() {
        onRefresh();
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                        >= mAdapter.getItemCount() - 2;
//                Logger.i("lastPostion:" + lastPostion);
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
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
        WebActivity.startWebView(getActivity(), bean.desc, bean.url);
    }

}
