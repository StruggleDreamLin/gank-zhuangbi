package com.practice.dreamlin.gankgirl;

import android.app.Application;

/**
 * Created by dreamlin on 2017/12/19.
 */

public class App extends Application {

    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static App getInstance(){
        return INSTANCE;
    }


}
