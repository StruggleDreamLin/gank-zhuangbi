package com.practice.dreamlin.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.practice.dreamlin.base.Bean;
import com.practice.dreamlin.gankgirl.App;
import com.practice.dreamlin.gankgirl.mvp.bean.GankEnity;
import com.practice.dreamlin.gankgirl.mvp.bean.GankLatestDate;
import com.practice.dreamlin.util.SharePreferencesUtil;
import com.practice.dreamlin.zhuangbi.mvp.bean.ZhuangBiBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dreamlin on 2017/12/20.
 */

public class DataBase {

    private static final String DATA_FILE_NAME = "_data.db";

    File dataFile;
    Gson gson;

    private static final Object locker = new Object();

    private DataBase() {
        gson = new Gson();
    }

    public static DataBase getInstance() {
        return InnerHolder.single;
    }

    private static class InnerHolder {
        private static final DataBase single = new DataBase();
    }


    public List<GankEnity> readGankItems(String category) {

        //判断是否有更新的数据需要重新加载

        String localDate = SharePreferencesUtil.getCategoryDate(category);
        if (!localDate.equals(GankLatestDate.latestDate)) {
            return null; //需要更新
        }
        Reader reader = readGsonItems(category);
        if (reader != null) {
            return gson.fromJson(reader, new TypeToken<List<GankEnity>>() {
            }.getType());
        }
        return null;
    }

    public List<ZhuangBiBean> readZbItems(String key) {

        //是否需要更新

        Reader reader = readGsonItems(key);

        if (reader != null) {
            return gson.fromJson(reader, new TypeToken<List<ZhuangBiBean>>() {
            }.getType());
        }
        return null;
    }

    //反序列化
    private Reader readGsonItems(String key) {

        synchronized (locker) {

            dataFile = new File(App.getInstance().getFilesDir(), key.toLowerCase().concat(DATA_FILE_NAME));
            try {
                Reader reader = new FileReader(dataFile);
                return reader;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    //序列化
    public void writeGsonItems(String category, Object items) {
        synchronized (locker) {
            String json = gson.toJson(items);
            dataFile = new File(App.getInstance().getFilesDir(), category.toLowerCase().concat(DATA_FILE_NAME));

            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Writer writer = new FileWriter(dataFile);
                writer.write(json);
                writer.flush();

                SharePreferencesUtil.putShare(category, GankLatestDate.latestDate);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(String category) {
        synchronized (locker) {
            dataFile = new File(App.getInstance().getFilesDir(), category.toLowerCase().concat(DATA_FILE_NAME));
            if (dataFile.exists()) {
                dataFile.delete();
            }
        }
    }

}
