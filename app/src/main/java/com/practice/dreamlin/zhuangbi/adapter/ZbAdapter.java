package com.practice.dreamlin.zhuangbi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.practice.dreamlin.gankgirl.R;
import com.practice.dreamlin.widget.RadioImageView;
import com.practice.dreamlin.zhuangbi.mvp.bean.ZhuangBiBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dreamlin on 2017/12/20.
 */

public class ZbAdapter extends RecyclerView.Adapter<ZbAdapter.ZbBaseHolder> {

    private List<ZhuangBiBean> gankList;
    private Context mContext;
    private ItemClicked itemListener;
    private RequestOptions options;

    public ZbAdapter(Context context) {
        mContext = context;
        options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        gankList = new ArrayList<>();
    }

    @Override
    public ZbBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_zb_image, parent, false);
        return new ZbHolder(view);
    }

    @Override
    public void onBindViewHolder(ZbBaseHolder holder, int position) {
        holder.bindItem(mContext, gankList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return gankList.size();
    }

    abstract class ZbBaseHolder extends RecyclerView.ViewHolder {

        public ZbBaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        abstract void bindItem(Context context, ZhuangBiBean bean, int position);
    }

    class ZbHolder extends ZbBaseHolder {

        @BindView(R.id.ivzb)
        ImageView ivZb;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        public ZbHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bindItem(Context context, ZhuangBiBean bean, int position) {

            if (options != null) {
                Glide.with(mContext)
                        .load(bean.url)
                        .into(ivZb);
            }
            ivZb.setOnClickListener((view) -> {
                if (itemListener != null)
                    itemListener.onItemClick(gankList.get(position), position);
            });
            tvDesc.setText(bean.desc);
        }
    }

    /**
     * Removes all of the elements from this list, and add new content with param list
     */
    public synchronized void addAll(List<ZhuangBiBean> list) {
        this.gankList.clear();
        appendAll(list);
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list
     *
     * @param list
     */
    public synchronized void appendAll(List<ZhuangBiBean> list) {
        this.gankList.addAll(list);
        notifyDataSetChanged();
    }

    public void setItemListener(ItemClicked listener) {
        this.itemListener = listener;
    }

    public interface ItemClicked {

        void onItemClick(ZhuangBiBean bean, int position);
    }

}
