package com.practice.dreamlin.gankgirl.mvp.p;

import com.practice.dreamlin.base.BasePresenter;
import com.practice.dreamlin.gankgirl.mvp.bean.GankLatestDate;
import com.practice.dreamlin.gankgirl.mvp.bean.History;
import com.practice.dreamlin.net.NetUtils;
import com.practice.dreamlin.util.Logger;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dreamlin on 2017/12/23.
 */

public class HistoryPresenter extends BasePresenter {


    public void getHistory() {

        Observable<History> history = NetUtils.getGank().getHistory();
        history.subscribeOn(Schedulers.io())
                .map(history1 -> history1.results.get(0))
                .subscribe(s -> GankLatestDate.latestDate = s,
                        throwable -> Logger.e(throwable.getMessage()));

    }


}
