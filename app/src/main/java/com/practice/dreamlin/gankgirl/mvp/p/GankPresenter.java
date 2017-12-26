package com.practice.dreamlin.gankgirl.mvp.p;

import com.practice.dreamlin.base.BasePresenter;
import com.practice.dreamlin.gankgirl.App;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.net.NetUtils;
import com.practice.dreamlin.db.DataBase;
import com.practice.dreamlin.gankgirl.mvp.bean.GankBean;
import com.practice.dreamlin.gankgirl.mvp.bean.GankEnity;
import com.practice.dreamlin.gankgirl.mvp.bean.ResultsBean;
import com.practice.dreamlin.gankgirl.mvp.v.IGankView;
import com.practice.dreamlin.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by dreamlin on 2017/12/20.
 */

/**
 * 缓存策略吧,
 * 从网络获取后序列化存储到本地数据库
 * 加载顺序：
 * .Memory
 * .Disk
 * .Network
 */
public class GankPresenter extends BasePresenter<IGankView> {

    private final int LOAD_DATA_COUNT = 50; //每次加载数据数量
    private final int MAX_PAGE_COUNT = 10;  //最大加载页数
    private final int DISPLAY_COUNT_PREPAGE = 20;//每页显示数量
    private int mCurrentPage = 0; //当前加载页数
    private List<GankEnity> mGankList;

    private Disposable mDisposable;
    BehaviorSubject<List<GankEnity>> cache;

    public GankPresenter(IGankView view) {
        mView = view;
        mGankList = new ArrayList<>();
    }

    /**
     * 类创建后refreshData会先于loadMore执行,所以先在这里做三级加载
     *
     * @param category
     */
    public Disposable refreshData(String category) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
//        DataBase.getInstance().delete();
        if (cache == null) {
            cache = BehaviorSubject.create();
            Observable.create(new ObservableOnSubscribe<List<GankEnity>>() {
                @Override
                public void subscribe(ObservableEmitter<List<GankEnity>> e) throws Exception {
                    List<GankEnity> items = DataBase.getInstance().readGankItems(category);
                    if (items == null) {
                        loadFromNetwork(category);
                    } else {
                        mGankList.clear();
                        mGankList.addAll(items);
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
                .subscribe(gankEnities -> {
                    mView.onFinish();
                    if (gankEnities == null || gankEnities.size() == 0) {
                        mView.onError(App.getInstance().getString(R.string.find_no_result));
                    } else {
                        mView.addAllData(gankEnities);
                    }
                }, throwable -> {
                    mView.onFinish();
                    mView.onError(App.getInstance().getString(R.string.find_no_result));
                });
        return mDisposable;
    }

    public Disposable loadMoreData(String category, int currentSize) {
        // 当前显示页数,1 - (MAX_PAGE_COUNT * LOAD_DATA_COUNT) / DISPLAY_COUNT_PREPAGE)
        int page = currentSize / DISPLAY_COUNT_PREPAGE + 1;
        if (page >= (MAX_PAGE_COUNT * LOAD_DATA_COUNT) / DISPLAY_COUNT_PREPAGE) {
            mView.noMoreData(App.getInstance().getString(R.string.no_more_data));
            return mDisposable;
        }
        //无需从网络拉取更多数据
        if (mGankList.size() >= DISPLAY_COUNT_PREPAGE * page) {
            //分页处理
            loadNextPage(page);
            return mDisposable;
        }
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        //从网络拉取更多数据
        cache = BehaviorSubject.create();
        mDisposable = cache.doOnError(throwable -> Logger.e(throwable.getMessage()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankEnities -> {
                    mView.onFinish();
                    mView.appendMoreData(gankEnities);
                }, throwable -> {
                    mView.onFinish();
                    mView.onError(throwable.getMessage());
                });
        loadFromNetwork(category);
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
                    mView.appendMoreData(mGankList.subList(fromIndex, fromIndex + DISPLAY_COUNT_PREPAGE));
                    mView.onFinish();
                });

    }

    Disposable loadFromNetwork(String category) {

        mCurrentPage = mGankList.size() / LOAD_DATA_COUNT;
        Observable<GankBean> data = NetUtils.getGank().getData(category, LOAD_DATA_COUNT, mCurrentPage + 1);
        mDisposable = data.subscribeOn(Schedulers.io())
                .map(gankBean -> gankBean.getResults())
                .flatMap(list -> Observable.fromArray(enityMapper(list)))
                .doOnNext(gankEnities -> {
                    Logger.i("IOthreadId:" + Thread.currentThread().getId());
                    DataBase.getInstance().writeGsonItems(category, mGankList); //序列化到本地
                })
                .subscribe((gankEnities) -> { //onNext
                    Logger.i("cache.onNext.ID:" + Thread.currentThread().getId());
                    cache.onNext(gankEnities);
                    mCurrentPage++;
                }, (throwable) -> { //onError
                    if (cache != null)
                        cache.onError(throwable);
                });
        return mDisposable;
    }

    List<GankEnity> enityMapper(List<ResultsBean> list) {
        List<GankEnity> gankEnities = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GankEnity enity = new GankEnity();
            enity.url = list.get(i).url;
            enity.desc = list.get(i).desc;
            enity.who = list.get(i).who;
            enity.type = list.get(i).type;
            gankEnities.add(enity);
        }
        int fromIndex = mGankList.size() / DISPLAY_COUNT_PREPAGE * DISPLAY_COUNT_PREPAGE;
        mGankList.addAll(gankEnities);
        int toIndex = mGankList.size() > (fromIndex + DISPLAY_COUNT_PREPAGE) ?
                DISPLAY_COUNT_PREPAGE + fromIndex : mGankList.size();
        return mGankList.subList(fromIndex, toIndex);
    }

}
