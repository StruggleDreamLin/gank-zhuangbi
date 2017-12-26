package com.practice.dreamlin.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.practice.dreamlin.gankgirl.App;

import java.util.Set;

/**
 * Created by dreamlin on 2017/12/23.
 */

public class SharePreferencesUtil {

    private static final String SHARE_FILE_NAME = "com_dreamlin_gank";

    private static SharedPreferences getShared() {
        return App.getInstance().getSharedPreferences(SHARE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void putShare(String key, Object value) {

        SharedPreferences.Editor editor = getShared().edit();
        if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }
//        editor.apply(); //不会提示任何信息
        editor.commit(); //return boolean tips success or fail
    }

    public static Object getShare(String key, Object defValue) {

        if (defValue instanceof Integer) {
            return getShared().getInt(key, (int) defValue);
        } else if (defValue instanceof Float) {
            return getShared().getFloat(key, (float) defValue);
        } else if (defValue instanceof Long) {
            return getShared().getLong(key, (long) defValue);
        } else if (defValue instanceof Boolean) {
            return getShared().getBoolean(key, (boolean) defValue);
        } else if (defValue instanceof String) {
            return getShared().getString(key, String.valueOf(defValue));
        } else if (defValue instanceof Set) {
            return getShared().getStringSet(key, (Set<String>) defValue);
        }
        return defValue;
    }

    public static String getCategoryDate(String category) {

        return (String) getShare(category, "");
    }

}
