package com.practice.dreamlin.gankgirl.mvp.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class Category {

    public @SerializedName("Android") String android;
    public @SerializedName("iOS") String ios;
    public @SerializedName("福利") String girl;
    public @SerializedName("前端") String web;
    public @SerializedName("瞎推荐") String recommend;
    public @SerializedName("休息视频") String restvideo;
    public @SerializedName("扩展资源") String extend;

    public static boolean isGirl(GankEnity gankEnity){
        return gankEnity.type.equals("福利");
    }

    public static boolean isAndroid(GankEnity gankEnity) {
        return gankEnity.type.equals("Android");
    }

    public static boolean isiOS(GankEnity gankEnity){
        return gankEnity.type.equals("iOS");
    }

    public static boolean isWeb(GankEnity gankEnity){
        return gankEnity.type.equals("前端");
    }

    public static boolean isVideo(GankEnity gankEnity){
        return gankEnity.type.equals("休息视频");
    }

    public static boolean isDefault(GankEnity gankEnity){
        return !(isGirl(gankEnity) || isAndroid(gankEnity) || isiOS(gankEnity) || isVideo(gankEnity) || isWeb(gankEnity));
    }

}
