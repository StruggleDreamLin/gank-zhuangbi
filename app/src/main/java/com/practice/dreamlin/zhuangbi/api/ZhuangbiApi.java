package com.practice.dreamlin.zhuangbi.api;

import com.practice.dreamlin.zhuangbi.mvp.bean.ZhuangBiBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dreamlin on 2017/12/25.
 */

public interface ZhuangbiApi {

    @GET("search")
    Observable<List<ZhuangBiBean>> getZhuangbi(@Query("q") String keywords);

}
