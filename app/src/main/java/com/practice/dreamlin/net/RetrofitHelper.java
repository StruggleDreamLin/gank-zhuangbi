package com.practice.dreamlin.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.practice.dreamlin.config.ConfigSetting;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class RetrofitHelper {

    private OkHttpClient mOkHttpClient;

    private RetrofitHelper(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        if (ConfigSetting.debug){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        mOkHttpClient = builder.build();
    }

    public static RetrofitHelper getInstance(){
        return InnerHolder.single;
    }

    public Retrofit newRetrofit(String baseUrl){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }

    private static class InnerHolder{

        private static final RetrofitHelper single = new RetrofitHelper();

    }
}
