package com.practice.dreamlin.gankgirl.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/20.
 */

public class History {

    public @SerializedName("error") boolean error;
    public @SerializedName("results")
    List<String> results;


}
