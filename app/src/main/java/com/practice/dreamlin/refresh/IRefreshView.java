package com.practice.dreamlin.refresh;

import com.practice.dreamlin.base.IBaseView;

/**
 * Created by dreamlin on 2017/12/20.
 */

public interface IRefreshView extends IBaseView {

    void onRefresh();

    void onLoadmore();

    void noMoreData(String nomore);

    void onFinish();

    void onError(String error);

}
