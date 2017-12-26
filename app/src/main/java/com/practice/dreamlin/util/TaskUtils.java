package com.practice.dreamlin.util;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by dreamlin on 2017/12/23.
 */

public class TaskUtils {

    @SafeVarargs public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params){
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            }else{
                task.execute(params);
            }
    }


}
