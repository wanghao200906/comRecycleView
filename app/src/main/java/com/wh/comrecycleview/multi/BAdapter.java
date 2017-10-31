package com.wh.comrecycleview.multi;

import android.util.Log;
import android.widget.Button;

import com.recycleView.ViewHolder;
import com.recycleView.delegate.AdapterDelegate;
import com.recycleView.utils.AdapterUtils;
import com.wh.comrecycleview.R;

import java.util.List;

/**
 * Created by wanghao on 2017/10/24.
 */

public class BAdapter extends AdapterDelegate<String> {

    private static final String TAG = "BAdapter";

    @Override
    public int getItemViewLayoutId() {
        return R.layout.view_rv_item2;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return (position % 2) != 0;
    }

    @Override
    public void onBind(ViewHolder holder, String o, int position, List<Object> payloads) {
        Button button = holder.getView(R.id.item_tv2);
        button.setText("button:" + o);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d(TAG, "onViewAttachedToWindow: ");
    }
}
