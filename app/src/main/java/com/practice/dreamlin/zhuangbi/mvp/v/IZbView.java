package com.practice.dreamlin.zhuangbi.mvp.v;

import com.practice.dreamlin.refresh.IRefreshView;
import com.practice.dreamlin.zhuangbi.mvp.bean.ZhuangBiBean;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/25.
 */

public interface IZbView extends IRefreshView {

    void showError(String error);

    void appendMoreData(List<ZhuangBiBean> biBeans);

    void addAllData(List<ZhuangBiBean> biBeans);

}
