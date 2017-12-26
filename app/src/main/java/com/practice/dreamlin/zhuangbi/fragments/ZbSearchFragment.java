package com.practice.dreamlin.zhuangbi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ZbSearchFragment extends TabFragment<ZbAdapter, ZbPresenter> implements IZbView, ZbAdapter.ItemClicked {

    SearchView mSearchView;
    String keywords;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    protected void loadData() {

    }

    public void initSearchView(SearchView searchView) {
        if (searchView != null) {
            setSearchView(searchView);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_zb_search;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void setSearchView(SearchView searchView) {
        mSearchView = searchView;
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setIconified(false); //开始处于展开状态
        mSearchView.onActionViewExpanded();//当无输入，隐藏清楚图标
//        mSearchView.setIconifiedByDefault(true);// true 搜索图标在框内
        mSearchView.setQueryHint(getString(R.string.enter_search));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSwipeRefreshLayout.setEnabled(true);
                keywords = query;
                onRefresh();
                mPresenter.refreshData(query);
                mSearchView.setIconified(true); //开始处于展开状态
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onLoadmore() {
        mSwipeRefreshLayout.setEnabled(true);
        super.onLoadmore();
        mPresenter.loadMoreData(keywords, mAdapter.getItemCount());
    }

    @Override
    public void noMoreData(String nomore) {
        super.noMoreData(nomore);
        super.hideRefreshWithDisabled();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ZbPresenter(this, this.getActivity());
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
                        >= mAdapter.getItemCount() - 1;
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
    public void showError(String error) {
        super.onError(error);
    }

    @Override
    public void appendMoreData(List<ZhuangBiBean> biBeans) {
        mAdapter.appendAll(biBeans);
        super.hideRefreshWithDisabled();
    }

    @Override
    public void addAllData(List<ZhuangBiBean> biBeans) {
        mAdapter.addAll(biBeans);
        super.hideRefreshWithDisabled();
    }

    @Override
    public void onItemClick(ZhuangBiBean bean, int position) {
        Snackbar.make(mRecyclerView,
                getString(R.string.share_or_save), Snackbar.LENGTH_LONG)
                .setAction(R.string.action_save, v -> mPresenter.saveImage(bean.url))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
