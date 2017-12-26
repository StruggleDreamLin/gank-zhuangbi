package com.practice.dreamlin.zhuangbi.mvp.p;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.practice.dreamlin.base.BasePresenter;
import com.practice.dreamlin.db.DataBase;
import com.practice.dreamlin.gankgirl.App;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.net.NetUtils;
import com.practice.dreamlin.util.Logger;
import com.practice.dreamlin.util.TaskUtils;
import com.practice.dreamlin.util.Utils;
import com.practice.dreamlin.zhuangbi.mvp.bean.ZhuangBiBean;
import com.practice.dreamlin.zhuangbi.mvp.v.IZbView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by dreamlin on 2017/12/25.
 */

public class ZbPresenter extends BasePresenter<IZbView> {

    public static final int SHARE_CODE = 0x10;
    private final int DISPLAY_COUNT_PREPAGE = 20;//每页显示数量
    private List<ZhuangBiBean> mZbList;
    private String lastKey = "";

    private Disposable mDisposable;
    BehaviorSubject<List<ZhuangBiBean>> cache;

    private Activity mContext;

    public ZbPresenter(IZbView view, Activity context) {
        mView = view;
        mContext = context;
        mZbList = new ArrayList<>();
    }

    /**
     * 类创建后refreshData会先于loadMore执行,所以先在这里做三级加载
     *
     * @param key
     */
    public Disposable refreshData(String key) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
//        DataBase.getInstance().delete();
        if (!lastKey.equals(key)) { //与上次搜索关键词不一致时重新创建,否则直接读取内存或者Disk
            cache = BehaviorSubject.create();
            Observable.create(new ObservableOnSubscribe<List<ZhuangBiBean>>() {
                @Override
                public void subscribe(ObservableEmitter<List<ZhuangBiBean>> e) throws Exception {
                    List<ZhuangBiBean> items = DataBase.getInstance().readZbItems(key);
                    if (items == null) {
                        loadFromNetwork(key);
                    } else {
                        mZbList.clear();
                        mZbList.addAll(items);
                        int toIndex = items.size() >= DISPLAY_COUNT_PREPAGE ? DISPLAY_COUNT_PREPAGE : items.size();
                        e.onNext(items.subList(0, toIndex));
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(cache);
        } else { // from Memory

        }

        mDisposable = cache.doOnError(throwable -> Logger.e(throwable.getMessage()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zbItems -> {
                    mView.onFinish();
                    if (zbItems == null || zbItems.size() == 0) {
                        mView.onError(App.getInstance().getString(R.string.find_no_result));
                    } else {
                        mView.addAllData(zbItems);
                    }
                }, throwable -> {
                    mView.onFinish();
                    mView.onError(App.getInstance().getString(R.string.find_no_result));
                });
        return mDisposable;
    }

    public Disposable loadMoreData(String key, int currentSize) {
        // 当前显示页数,1 - (MAX_PAGE_COUNT * LOAD_DATA_COUNT) / DISPLAY_COUNT_PREPAGE)
        int page = currentSize / DISPLAY_COUNT_PREPAGE + 1;
        if (currentSize >= mZbList.size()) {
            mView.noMoreData(App.getInstance().getString(R.string.no_more_data));
            return mDisposable;
        }
        //无需从网络拉取更多数据
        loadNextPage(page);
        return mDisposable;
    }

    /**
     * 显示下一页
     *
     * @param page
     */
    void loadNextPage(int page) {
        int fromIndex = (page - 1) * DISPLAY_COUNT_PREPAGE;
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    int toIndex = fromIndex + DISPLAY_COUNT_PREPAGE > mZbList.size() ?
                            fromIndex + DISPLAY_COUNT_PREPAGE : mZbList.size();
                    mView.appendMoreData(mZbList.subList(fromIndex, toIndex));
                    mView.onFinish();
                });

    }

    Disposable loadFromNetwork(String keywords) {

        Observable<List<ZhuangBiBean>> data = NetUtils.getZb().getZhuangbi(keywords);
        mDisposable = data.subscribeOn(Schedulers.io())
                .map(biBeans -> enityMapper(biBeans))
                .doOnNext(zbItems -> {
                    Logger.i("IOthreadId:" + Thread.currentThread().getId());
                    DataBase.getInstance().writeGsonItems(keywords, mZbList); //序列化到本地
                })
                .subscribe((zbItems) -> { //onNext
                    cache.onNext(zbItems);
                    lastKey = keywords;
                }, (throwable) -> { //onError
                    if (cache != null)
                        cache.onError(throwable);
                });
        return mDisposable;
    }

    List<ZhuangBiBean> enityMapper(List<ZhuangBiBean> list) {

        int fromIndex = mZbList.size() / DISPLAY_COUNT_PREPAGE * DISPLAY_COUNT_PREPAGE;
        mZbList.addAll(list);
//        mZbList.add(new ZhuangBiBean("欺负老实人吗",
//                "https://www.zhuangbi.info/uploads/i/2017-12-13-6690e94d19e5cc0a5c2547b55f890d19.gif"));
        int toIndex = mZbList.size() > fromIndex + DISPLAY_COUNT_PREPAGE ?
                fromIndex + DISPLAY_COUNT_PREPAGE : mZbList.size();
        return mZbList.subList(fromIndex, toIndex);
    }

    public void saveImage(String url) {
        Utils.saveImage(mContext, "Zhuangbi", url, message -> mView.showError(message));
    }

}
