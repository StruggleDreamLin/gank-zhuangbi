package com.practice.dreamlin.gankgirl.mvp.v;

import com.practice.dreamlin.gankgirl.mvp.bean.GankEnity;
import com.practice.dreamlin.refresh.IRefreshView;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/20.
 */

public interface IGankView extends IRefreshView {

    /**
     * 显示错误信息
     * @param error
     */
    void showError(String error);

    /**
     * 追加更多数据
     * @param list
     */
    void appendMoreData(List<GankEnity> list);

    /**
     * 添加全部数据
     * @param list
     */
    void addAllData(List<GankEnity> list);

}
