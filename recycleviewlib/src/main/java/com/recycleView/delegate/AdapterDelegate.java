package com.recycleView.delegate;

import com.recycleView.ViewHolder;

import java.util.List;

/**
 * Created by wanghao on 2017/10/30.
 */

public abstract class AdapterDelegate<T> implements ItemViewDelegate<T> {
    @Override
    public abstract void onBind(ViewHolder holder, T t, int position, List<Object> payloads) ;

    @Override
    public abstract int getItemViewLayoutId();

    @Override
    public abstract boolean isForViewType(T item, int position);

    @Override
    public void onViewRecycled(ViewHolder viewHolder) {

    }

    @Override
    public boolean onFailedToRecycleView(ViewHolder holder) {
        return false;
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {

    }

}
