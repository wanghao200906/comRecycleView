package com.recycleView;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.recycleView.click.ItemClickListener;
import com.recycleView.click.ItemLongClickListener;
import com.recycleView.click.OnLoadMoreListener;
import com.recycleView.click.ViewClickListener;
import com.recycleView.delegate.AdapterDelegate;
import com.recycleView.delegate.ItemViewDelegate;
import com.recycleView.delegate.ItemViewDelegateManager;

import java.util.List;

/**
 * Created by wanghao on 2017/10/13.
 */

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final int ITEM_HEADER = 100000;
    private static final int ITEM_FOOTER = 200000;
    private static final int ITEM_LOADMORE = 300000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    private static final String TAG = "BaseRecyclerViewAdapter";

    //存储监听回调
    private SparseArray<ViewClickListener> onViewClickListeners;
    private ItemLongClickListener onLongClickListeners;
    private ItemClickListener onClickListeners;
    private List<T> mData;

    protected ItemViewDelegateManager delegatesManager;

    public BaseRecyclerViewAdapter(List<T> mData) {
        this.mData = mData;
        onViewClickListeners = new SparseArray<>();
        delegatesManager = new ItemViewDelegateManager();
    }


    public void setOnViewClickListener(int viewId, ViewClickListener listener) {
        ViewClickListener listener_ = onViewClickListeners.get(viewId);
        if (listener_ == null) {
            onViewClickListeners.put(viewId, listener);
        }
    }

    public void setOnItemLongClickListener(ItemLongClickListener listener) {
        this.onLongClickListeners = listener;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.onClickListeners = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " + viewType);
        if (mHeaderViews.get(viewType) != null) {
            return ViewHolder.getViewHolder(parent,mHeaderViews.get(viewType));
        }
        if (mFootViews.get(viewType) != null) {
            return ViewHolder.getViewHolder(parent,mFootViews.get(viewType));
        }
        if (viewType == ITEM_LOADMORE) {
            ViewHolder holder3;
            if (mLoadMoreView != null) {
                holder3 =ViewHolder.getViewHolder(parent,mLoadMoreView);
            } else {
                holder3 = ViewHolder.getViewHolder(parent, mLoadMoreLayoutId);
            }
            return holder3;
        }

        ItemViewDelegate delegateForViewType = delegatesManager.getItemViewDelegate(viewType);

        int layoutId = delegateForViewType.getItemViewLayoutId();
        return ViewHolder.getViewHolder(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        Log.d(TAG, "onBindViewHolder2: " + position);
        if (isShowLoadMore(position)) {
            Log.d(TAG, "onBindViewHolder isShowLoadMore: ");
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        if (isFooter(position)) {
            return;
        }

        if (isHeader(position)) {
            return;
        }

        int newPosition = position - getHeaderSize();
        T itemModel = getItem(newPosition);
        onBind(holder, itemModel, newPosition, payloads);//更新itemView视图
//        onBind(holder, itemModel);//更新itemView视图
        checkClickListener(holder, newPosition);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }


    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_LOADMORE;
        }
        if (isHeader(position)) {
            return mHeaderViews.keyAt(position);
        }
        if (isFooter(position)) {
            return mFootViews.keyAt(position - getHeaderSize() - getDataSize());
        }
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return delegatesManager.getItemViewType(
                mData.get(position - getHeaderSize()),
                position - getHeaderSize());
    }

    public BaseRecyclerViewAdapter<T> addItemViewDelegate(AdapterDelegate<T> itemViewDelegate) {
        delegatesManager.addDelegate(itemViewDelegate);
        return this;
    }

    public BaseRecyclerViewAdapter<T> addItemViewDelegate(int viewType, AdapterDelegate<T> itemViewDelegate) {
        delegatesManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return delegatesManager.getItemViewDelegateCount() > 0;
    }

    private void checkClickListener(ViewHolder holder, final int position) {
        //设置点击监听
        for (int i = 0; i < onViewClickListeners.size(); ++i) {
            int id = onViewClickListeners.keyAt(i);
            View view = holder.getView(id);
            if (view == null)
                continue;
            final ViewClickListener listener = onViewClickListeners.get(id);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClicked(v, position);
                    }
                }
            });
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickListeners != null) {

                    onLongClickListeners.onItemLongClicked(position);
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListeners != null) {
                    onClickListeners.onItemClicked(position);
                }
            }
        });
    }

    public T getItem(final int position) {
        if (mData == null)
            return null;
        return mData.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        delegatesManager.onAttachedToRecyclerView(recyclerView);

        Log.d(TAG, "onAttachedToRecyclerView: ");


        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            Log.d(TAG, "onAttachedToRecyclerView: " + gridManager.getSpanCount());
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    Log.d(TAG, "onAttachedToRecyclerView position : " + position);

                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return gridManager.getSpanCount();
                    }
                    if (mFootViews.get(viewType) != null) {
                        return gridManager.getSpanCount();
                    }
                    if (isShowLoadMore(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }

    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d(TAG, "onViewAttachedToWindow: ");
        delegatesManager.onViewAttachedToWindow(holder);
    }

    //    @Override
//    public void onViewAttachedToWindow(ViewHolder holder) {
//        delegatesManager.onViewAttachedToWindow(holder);
//        int position = holder.getLayoutPosition();
//        if (isFooter(position) || isHeader(position) || isShowLoadMore(position)) {
//            AdapterUtils.setFull(holder);
//        }
//    }


    @Override
    public int getItemCount() {
        int count = getHeaderSize() + getDataSize() + getFooterSize() + (hasLoadMore() ? 1 : 0);
        return count;
    }

    public int getDataSize() {
        return (mData != null) ? mData.size() : 0;
    }

    public void onBind(ViewHolder holder, T itemModel, int position, List<Object> payloads) {
        delegatesManager.onBind(holder, itemModel, position, payloads);
    }


    public void destroyAdapterListener() {
        if (onViewClickListeners != null)
            onViewClickListeners.clear();
        onViewClickListeners = null;

        if (mData != null)
            mData.clear();
        mData = null;
    }

    public void removeItem(int position) {

        notifyItemRemoved(position);
        mData.remove(position);
        if (position != mData.size()) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, mData.size() - position);
        }
    }


    public boolean isFooter(int position) {
        return position >= getHeaderSize() + getDataSize();
    }

    public boolean isHeader(int position) {

        return position < getHeaderSize();

    }

    public int getHeaderSize() {
        return mHeaderViews.size();
    }

    public int getFooterSize() {
        return mFootViews.size();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_HEADER, view);
    }

    public void addFooterView(View view) {
        mFootViews.put(mFootViews.size() + ITEM_FOOTER, view);
    }


    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= getDataSize() + getFooterSize() + getHeaderSize());
    }


    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
    }

    public void setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
    }

    public void setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
    }


}
