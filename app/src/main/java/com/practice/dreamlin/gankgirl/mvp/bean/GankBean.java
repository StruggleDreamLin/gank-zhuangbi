package com.practice.dreamlin.gankgirl.mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class GankBean {


    /**
     * error : false
     * results : [{"_id":"5a30a105421aa90fe2f02cd8","createdAt":"2017-12-13T11:39:49.295Z","desc":"图解ConcurrentHashMap","publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488667&idx=1&sn=9f17d205c9deff7b571c3280ca2b1e10","used":true,"who":"陈宇明"},{"_id":"5a33790f421aa90fe2f02ce0","createdAt":"2017-12-15T15:26:07.407Z","desc":"Android城市选择器源码实现","publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s/ndc2vSlQ0cG7uxj_eV5M1w","used":true,"who":"D_clock"},{"_id":"5a35f607421aa90fe2f02ce3","createdAt":"2017-12-17T12:43:51.659Z","desc":"图解RxJava2(一)","images":["http://img.gank.io/403efc72-288f-4c5d-9d6b-ab464e004ede"],"publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"http://rkhcy.github.io/2017/12/13/%E5%9B%BE%E8%A7%A3RxJava2(%E4%B8%80)/","used":true,"who":"HuYounger"},{"_id":"5a373497421aa90fe50c028d","createdAt":"2017-12-18T11:23:03.192Z","desc":"一个能让微信 Material Design 化的 Xposed 模块","images":["http://img.gank.io/061bc21f-be86-40a5-ab6a-ab7c26a8bc36"],"publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"https://github.com/Blankeer/MDWechat","used":true,"who":null},{"_id":"5a37a94a421aa90fef2035a9","createdAt":"2017-12-18T19:40:58.803Z","desc":"基于QQ、微信和微博原生api文档，整合的第三方分享和登陆框架","publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"https://github.com/arvinljw/SocialHelper","used":true,"who":"arvinljw"},{"_id":"5a37de68421aa90fe72536c3","createdAt":"2017-12-18T23:27:36.605Z","desc":"Android APP性能优化的一些思考","publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s/vFPMFd8pT1X9oq46waoLGA","used":true,"who":"D_clock"},{"_id":"5a3875e6421aa90fe72536c4","createdAt":"2017-12-19T10:13:58.688Z","desc":"酷酷的Android Loading动画，让用户摆脱无聊等待","publishedAt":"2017-12-19T12:00:28.893Z","source":"web","type":"Android","url":"http://mp.weixin.qq.com/s/S6NCv-o_kp22hFtwn-84Mg","used":true,"who":"D_clock"},{"_id":"5a2e4011421aa90fe2f02cd1","createdAt":"2017-12-11T16:21:37.459Z","desc":"我打赌你一定没搞明白的Activity启动模式","publishedAt":"2017-12-15T08:59:11.361Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488588&idx=1&sn=3f7c59654835ec8d560610ba97d10fc0","used":true,"who":"陈宇明"},{"_id":"5a2e6a38421aa90fe72536b0","createdAt":"2017-12-11T19:21:28.577Z","desc":"safe adb：在多设备时更方便地操作 adb，支持批量操作。","images":["http://img.gank.io/27c7d4a1-9c3e-42ed-9a21-051cd9f77798"],"publishedAt":"2017-12-15T08:59:11.361Z","source":"web","type":"Android","url":"https://github.com/linroid/sadb","used":true,"who":"drakeet"},{"_id":"5a2e80d4421aa90fe50c0272","createdAt":"2017-12-11T20:57:56.667Z","desc":"图解ConcurretHashMap","images":["http://img.gank.io/d00aec87-7573-45a0-87bc-f3046ad442d4"],"publishedAt":"2017-12-15T08:59:11.361Z","source":"web","type":"Android","url":"http://rkhcy.github.io/2017/12/06/%E5%9B%BE%E8%A7%A3ConcurrentHashMap/","used":true,"who":"HuYounger"}]
     */

    private @SerializedName("error") boolean error;
    private @SerializedName("results") List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

}

