package com.practice.dreamlin.gankgirl;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.practice.dreamlin.base.BaseAppCompatActivity;
import com.practice.dreamlin.gankgirl.fragments.GankCommonFragment;
import com.practice.dreamlin.gankgirl.fragments.GirlsFragment;
import com.practice.dreamlin.gankgirl.mvp.p.HistoryPresenter;
import com.practice.dreamlin.util.Logger;
import com.practice.dreamlin.web.WebActivity;
import com.practice.dreamlin.zhuangbi.ZhuangBiActivity;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity<HistoryPresenter>
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    String[] gankCategory;
    @BindView(R.id.fab)
    FloatingActionButton mActionButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolbar);

        gankCategory = getResources().getStringArray(R.array.gank_category);
        //set viewPage's content
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public android.app.Fragment getItem(int position) {
                switch (position) {
                    case 2:
                        return new GirlsFragment();

                    default:
                        GankCommonFragment gankCommonFragment = new GankCommonFragment();
                        gankCommonFragment.setCategory(gankCategory[position]);
                        return gankCommonFragment;
                }
            }

            @Override
            public int getCount() {
                return gankCategory.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return gankCategory[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        mActionButton.setOnClickListener(v ->
                PagerWatcher.notifyWatcher(mViewPager.getCurrentItem()));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new HistoryPresenter();
        mPresenter.getHistory();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gank) {


        } else if (id == R.id.nav_zb) {

            startActivity(new Intent(this, ZhuangBiActivity.class));

        } else if (id == R.id.nav_not_set) {

        } else if (id == R.id.nav_gank_site) {
            WebActivity.startWebView(this, getString(R.string.moudle_gank),
                    getString(R.string.gank_site));
        } else if (id == R.id.nav_zhuangbi_site) {
            WebActivity.startWebView(this, getString(R.string.moudle_zb),
                    getString(R.string.zhuangbi_site));
        } else if (id == R.id.nav_about_me) {
            WebActivity.startWebView(this, getString(R.string.about_me),
                    getString(R.string.github_site));
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERSSION_CODE) {
            Logger.i("授权结果:" + requestCode);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
