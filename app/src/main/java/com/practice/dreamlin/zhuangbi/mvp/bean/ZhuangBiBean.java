package com.practice.dreamlin.zhuangbi.mvp.bean;

import com.google.gson.annotations.SerializedName;
import com.practice.dreamlin.base.Bean;

/**
 * Created by dreamlin on 2017/12/25.
 */

public class ZhuangBiBean extends Bean {

    public @SerializedName("description")
    String desc;
    public @SerializedName("image_url")
    String url;

    public ZhuangBiBean(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }
}
