package com.practice.dreamlin.gankgirl.api;


import com.practice.dreamlin.gankgirl.mvp.bean.GankBean;
import com.practice.dreamlin.gankgirl.mvp.bean.History;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dreamlin on 2017/12/19.
 */

public interface GankApi {

    @GET("api/data/福利/{count}/{page}")
    Observable<GankBean> getGirls(@Path("count") int count, @Path("page") int page);

    @GET("api/data/Android/{count}/{page}")
    Observable<GankBean> getAndroid(@Path("count") int count, @Path("page") int page);

    @GET("api/data/iOS/{count}/{page}")
    Observable<GankBean> getiOS(@Path("count") int count, @Path("page") int page);

    @GET("api/data/前端/{count}/{page}")
    Observable<GankBean> getWeb(@Path("count") int count, @Path("page") int page);

    @GET("api/data/休息视频/{count}/{page}")
    Observable<GankBean> getRestVideo(@Path("count") int count, @Path("page") int page);

    @GET("api/data/瞎推荐/{count}/{page}")
    Observable<GankBean> getRecommend(@Path("count") int count, @Path("page") int page);

    @GET("day/history")
    Observable<History> getHistory();

    @GET("api/data/{category}/{count}/{page}")
    Observable<GankBean> getData(@Path("category") String category,
                                 @Path("count") int count,
                                 @Path("page") int page);

}
