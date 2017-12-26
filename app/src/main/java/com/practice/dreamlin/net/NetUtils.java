package com.practice.dreamlin.net;

import com.practice.dreamlin.gankgirl.api.GankApi;
import com.practice.dreamlin.zhuangbi.api.ZhuangbiApi;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class NetUtils {

    private static final Object locker = new Object();

    private static final String GANK = "http://gank.io/";
    private static GankApi gankApi;

    private static final String ZHUANGBI = "https://www.zhuangbi.info/";
    private static ZhuangbiApi zhuangbiApi;

    public final static GankApi getGank() {
        synchronized (locker) {
            if (gankApi == null) {
                gankApi = RetrofitHelper.getInstance()
                        .newRetrofit(NetUtils.GANK)
                        .create(GankApi.class);
            }
        }
        return gankApi;
    }

    public final static ZhuangbiApi getZb() {
        synchronized (locker) {
            if (zhuangbiApi == null) {
                zhuangbiApi = RetrofitHelper.getInstance()
                        .newRetrofit(ZHUANGBI)
                        .create(ZhuangbiApi.class);
            }
        }
        return zhuangbiApi;
    }


}
