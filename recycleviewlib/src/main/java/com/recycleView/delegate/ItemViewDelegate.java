package com.recycleView.delegate;


import com.recycleView.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    /**
     * 判断获取到的postion和item 是否要现在在当前的viewtype上
     *
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item, int position);

    void onBind(ViewHolder holder, T t, int position, List<Object> payloads);

    void onViewRecycled(ViewHolder viewHolder);

    boolean onFailedToRecycleView(ViewHolder holder);

    void onViewAttachedToWindow(ViewHolder holder);
    void onAttachedToRecyclerView(ViewHolder holder);

    public void onViewDetachedFromWindow(ViewHolder holder);
}