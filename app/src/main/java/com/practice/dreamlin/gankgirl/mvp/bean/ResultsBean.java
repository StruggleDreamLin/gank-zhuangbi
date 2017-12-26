package com.practice.dreamlin.gankgirl.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class ResultsBean {
    /**
     * _id : 5a30a105421aa90fe2f02cd8
     * createdAt : 2017-12-13T11:39:49.295Z
     * desc : 图解ConcurrentHashMap
     * publishedAt : 2017-12-19T12:00:28.893Z
     * source : web
     * type : Android
     * url : https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488667&idx=1&sn=9f17d205c9deff7b571c3280ca2b1e10
     * used : true
     * who : 陈宇明
     * images : ["http://img.gank.io/403efc72-288f-4c5d-9d6b-ab464e004ede"]
     */

    public @SerializedName("_id") String _id;
    public @SerializedName("createdAt") String createdAt;
    public @SerializedName("desc") String desc;
    public @SerializedName("publishedAt") String publishedAt;
    public @SerializedName("source") String source;
    public @SerializedName("type") String type;
    public @SerializedName("url") String url;
    public @SerializedName("used") boolean used;
    public @SerializedName("who") String who;
    public @SerializedName("images") List<String> images;

    public boolean isGirl(){
        return type.equals("福利");
    }

    public boolean isAndroid() {
        return type.equals("Android");
    }

    public boolean isiOS(){
        return type.equals("iOS");
    }

    public boolean isWeb(){
        return type.equals("前端");
    }

    public boolean isVideo(){
        return type.equals("休息视频");
    }

    public boolean isDefault(){
        return !(isGirl() || isAndroid() || isiOS() || isVideo() || isWeb());
    }
}
