package com.wh.comrecycleview.multi;

import com.recycleView.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by wanghao on 2017/10/24.
 */

public class MultiAdapter extends BaseRecyclerViewAdapter {
    public MultiAdapter(List<String> data) {
        super(data);
        addItemViewDelegate(new AAdapter());
        addItemViewDelegate(new BAdapter());
    }
}
