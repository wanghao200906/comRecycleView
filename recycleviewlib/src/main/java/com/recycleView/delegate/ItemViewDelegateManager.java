package com.recycleView.delegate;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.recycleView.ViewHolder;

import java.util.Collections;
import java.util.List;


/**
 * Created by zhy on 16/6/22.
 */
public class ItemViewDelegateManager<T> {
    private static final String TAG = "ItemViewDelegateManager";
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();
    private static final List<Object> PAYLOADS_EMPTY_LIST = Collections.emptyList();

    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public void onBind(ViewHolder holder, T item, int position, List<Object> payloads) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.onBind(holder, item, position,
                        payloads != null ? payloads : PAYLOADS_EMPTY_LIST);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        Log.d(TAG, "onViewAttachedToWindow: "+delegates.size());
        ItemViewDelegate<T> delegate = getItemViewDelegate(viewHolder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException("No delegate found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        delegate.onViewAttachedToWindow(viewHolder);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

    }
}
