package com.practice.dreamlin.zhuangbi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.practice.dreamlin.base.BaseAppCompatActivity;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.util.Logger;
import com.practice.dreamlin.zhuangbi.fragments.ZbCommonFragment;
import com.practice.dreamlin.zhuangbi.fragments.ZbSearchFragment;
import com.practice.dreamlin.zhuangbi.mvp.p.ZbPresenter;

import butterknife.BindView;

public class ZhuangBiActivity extends BaseAppCompatActivity<ZbPresenter> {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    private String[] fragments;
    ZbSearchFragment zbSearchFragment;
    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_zhuang_bi;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_zb_search;
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHideOnContentScrollEnabled(true);
        fragments = getResources().getStringArray(R.array.zhuangbi_keys);
        setTitle(fragments[0]);
        zbSearchFragment = new ZbSearchFragment();
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 3:
                        return zbSearchFragment;
                    default:
                        ZbCommonFragment zbCommonFragment = new ZbCommonFragment();
                        zbCommonFragment.setKeywords(fragments[position]);
                        return zbCommonFragment;

                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 3:
                        searchView.setVisibility(View.VISIBLE);
                        zbSearchFragment.initSearchView(searchView);
                        break;
                    default:
                        searchView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.i("state:" + state);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean create = super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setVisibility(View.INVISIBLE);
        return create;
    }

    @Override
    protected void initPresenter() {

    }
}
