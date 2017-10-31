package com.recycleView;

import com.recycleView.delegate.AdapterDelegate;

import java.util.List;

/**
 * Created by wanghao on 2017/10/24.
 */

public abstract class SimpleAdapter<T> extends BaseRecyclerViewAdapter<T> {

    public SimpleAdapter(final int layoutId, List<T> data) {
        super(data);

        addItemViewDelegate(new AdapterDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public void onBind(ViewHolder holder, T t, int position, List<Object> payloads) {
                SimpleAdapter.this.onBindView(holder, t, position);

            }


            @Override
            public boolean isForViewType(Object item, int position) {
                return true;
            }


        });
    }


    protected abstract void onBindView(ViewHolder holder, T t, int position);

}
