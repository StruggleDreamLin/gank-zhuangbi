package com.practice.dreamlin.gankgirl.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class TodayBean {

    public @SerializedName("category")
    List<String> category;
    public @SerializedName("error") boolean error;
    public @SerializedName("results")
    List<ResultsBean> results;

}
