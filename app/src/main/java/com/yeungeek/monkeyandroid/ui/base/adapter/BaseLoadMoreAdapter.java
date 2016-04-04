package com.yeungeek.monkeyandroid.ui.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yeungeek.monkeyandroid.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yeungeek on 2016/4/4.
 */
public abstract class BaseLoadMoreAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    public static final int TYPE_FOOTER = Integer.MIN_VALUE;
    public static final int TYPE_ITEM = 0;
    private boolean hasFooter;
    private boolean hasMoreData;

    private final List<T> mList = new LinkedList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_load_more, parent, false);
            return new FooterViewHolder(view);
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            if (hasMoreData) {
                ((FooterViewHolder) holder).itemProgressBar.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).itemContent.setText("加载中");
            } else {
                ((FooterViewHolder) holder).itemProgressBar.setVisibility(View.GONE);
                ((FooterViewHolder) holder).itemContent.setText("已经加载到底了哦");
            }
        } else {
            onBindItemViewHolder((VH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + (hasFooter ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getBasicItemCount() && hasFooter) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;//0
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.id_item_progress)
        ProgressBar itemProgressBar;
        @Bind(R.id.id_item_content)
        TextView itemContent;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindItemViewHolder(final VH holder, int position);

    //out
    public List<T> getList() {
        return mList;
    }

    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public void add(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
    }

    public void addTop(T item) {
        if (item == null) {
            return;
        }
        mList.add(0, item);
    }

    public void addAllTop(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
    }

    public void remove(int position) {
        if (position < mList.size() - 1 && position >= 0) {
            mList.remove(position);
        }
    }

    public void clear() {
        mList.clear();
    }

    //end out

    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        if (this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean isMoreData) {
        if (this.hasMoreData != isMoreData) {
            this.hasMoreData = isMoreData;
            notifyDataSetChanged();
        }
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if (this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    private int getBasicItemCount() {
        return mList.size();
    }
}
