package com.practice.dreamlin.gankgirl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.gankgirl.mvp.bean.Category;
import com.practice.dreamlin.gankgirl.mvp.bean.GankEnity;
import com.practice.dreamlin.util.Logger;
import com.practice.dreamlin.widget.CircleImage;
import com.practice.dreamlin.widget.RadioImageView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dreamlin on 2017/12/20.
 */

public class GankCommonAdapter extends RecyclerView.Adapter<GankCommonAdapter.GankHolder> {

    private List<GankEnity> gankList;
    private Context mContext;
    private ItemClicked itemListener;
    private RequestOptions options;

    public GankCommonAdapter(Context context) {
        mContext = context;
        options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop();
        gankList = new ArrayList<>();
    }

    @Override
    public GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == GankType.Girl.ordinal()) {
//            view = View.inflate(mContext, R.layout.item_gank_girl, null);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_girl, parent, false);
            return new GirlsHolder(view);
        } else if (viewType == GankType.Common.ordinal()) {
//            view = View.inflate(mContext, R.layout.item_gank_common, null);
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_common, parent, false);
            return new CommonHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GankHolder holder, int position) {
        holder.bindItem(mContext, gankList.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {

        return Category.isGirl(gankList.get(position)) ? GankType.Girl.ordinal() : GankType.Common.ordinal();

    }

    @Override
    public int getItemCount() {
        return gankList.size();
    }

    abstract class GankHolder extends RecyclerView.ViewHolder {

        public GankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void bindItem(Context context, GankEnity bean, int position);
    }

    class GirlsHolder extends GankHolder {

        @BindView(R.id.iv_girl)
        RadioImageView ivGirl;

        public GirlsHolder(View itemView) {
            super(itemView);
            ivGirl.setOriginalSize(50, 50);
        }

        @Override
        void bindItem(Context context, GankEnity bean, int position) {

            if (options != null) {
                Glide.with(mContext)
                        .load(bean.url)
                        .apply(options)
                        .into(ivGirl)
                        .getSize((width, height) -> {
                            //with size do something
                        });
            }
            ivGirl.setOnClickListener((view) -> {
                if (itemListener != null)
                    itemListener.onItemClick(gankList.get(position), position);
            });

        }
    }

    class CommonHolder extends GankHolder {

        @BindView(R.id.iv_type)
        CircleImage ivType;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        public CommonHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bindItem(Context context, GankEnity bean, int position) {
            itemView.setOnClickListener((view) -> {
                if (itemListener != null)
                    itemListener.onItemClick(gankList.get(position), position);
            });
            if (Category.isAndroid(bean)) {
                ivType.setImageResource(R.mipmap.android);
            } else if (Category.isiOS(bean)) {
                ivType.setImageResource(R.mipmap.ios);
            } else if (Category.isWeb(bean)) {
                ivType.setImageResource(R.mipmap.web);
            } else if (Category.isVideo(bean)) {
                ivType.setImageResource(R.mipmap.tv);
            } else {
                ivType.setImageResource(R.mipmap.def);
            }
            tvDesc.setText(bean.desc);
        }
    }

    /**
     * Removes all of the elements from this list, and add new content with param list
     */
    public synchronized void addAll(List<GankEnity> list) {
        this.gankList.clear();
        appendAll(list);
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list
     *
     * @param list
     */
    public synchronized void appendAll(List<GankEnity> list) {
        this.gankList.addAll(list);
        notifyDataSetChanged();
    }

    enum GankType {
        Common,
        Girl
    }

    public void setItemListener(ItemClicked listener) {
        this.itemListener = listener;
    }

    public interface ItemClicked {

        void onItemClick(GankEnity bean, int position);
    }

}
