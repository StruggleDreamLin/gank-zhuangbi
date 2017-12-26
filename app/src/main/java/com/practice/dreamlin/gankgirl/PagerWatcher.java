package com.practice.dreamlin.gankgirl;

import com.practice.dreamlin.refresh.fragment.RefreshFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dreamlin on 2017/12/21.
 */

public class PagerWatcher {

    private static List<RefreshFragment> refreshFragmentList = new ArrayList<>();


    public static void addWatcher(RefreshFragment fragment) {
        if (!refreshFragmentList.contains(fragment))
            refreshFragmentList.add(fragment);
    }

    public static void notifyWatcher(int position){
        if(position < refreshFragmentList.size()){
            refreshFragmentList.get(position).onNotify();
        }
    }

}
