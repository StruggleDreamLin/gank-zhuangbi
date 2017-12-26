package com.practice.dreamlin.util;

import android.util.Log;

import com.practice.dreamlin.config.ConfigSetting;
import com.practice.dreamlin.gankgirl.App;

/**
 * Created by dreamlin on 2017/12/21.
 */

public class Logger {

    private final static String TAG = App.getInstance().getPackageName().concat("Logger");

    public static void i(String log){
        if (ConfigSetting.debug){
            Log.i(TAG, log);
        }
    }

    public static void d(String dlog){
        if (ConfigSetting.debug){
            Log.d(TAG, dlog);
        }
    }

    public static void e(String error){
        if (ConfigSetting.debug){
            Log.e(TAG, error);
        }
    }

}
