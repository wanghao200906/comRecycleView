package com.wh.comrecycleview.dividerdecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.recycleView.BaseRecyclerViewAdapter;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by wanghao on 2017/10/26.
 */

public abstract class GridStickySectionDecoration extends   LinearStickySectionDecoration  {
    private int itemTotalCount;
    private int span;

    private static final String TAG = "GridStickySectionDecora";
    public GridStickySectionDecoration(int itemTotalCount, int span) {
        this.itemTotalCount = itemTotalCount;
        this.span = span;
        for (int pos = 0; pos < itemTotalCount; pos++) {
            /*我们为每个不同头部名称的第一个item设置头部高度*/
            String curHeaderName = getRealHeaderName(pos);         //根据j获取要悬浮的头部名
            if (curHeaderName == null) {
                return;
            }
            if (!headerPaddingSet.contains(pos) && (pos == 0 || !curHeaderName.equals(getRealHeaderName(pos - 1)))) {//如果是分组头部
                groupHeadPos.add(pos);
                for (int i = 0; i < span; i++) {
                    headerPaddingSet.add(pos + i);
                    Log.w(TAG, "headerPaddingArray put--->" + (pos + i));
                    if (!curHeaderName.equals(getRealHeaderName(pos + i + 1))) {//如果下一个分组名称不一致，pass
                        break;
                    }
                }
            }
            if (!curHeaderName.equals(getRealHeaderName(pos + 1)) && groupHeadPos.size() > 0) {
                int preHeadPos = (int) ((TreeSet) (groupHeadPos)).last();
                int padSpan = span - (pos - preHeadPos) % span;
                headerSpanArray.put(pos, padSpan);
                Log.w(TAG, "headerSpanArray put--->" + pos + "--->" + padSpan);
            }
        }
    }

    private Set<Integer> headerPaddingSet = new TreeSet<>();                //用来记录每个头部的paddintTop信息
    private Set<Integer> groupHeadPos = new TreeSet<>();                    //记录每个分组第一个头部的pos【用于计算当前组最后一个item的span】
    private SparseArray<Integer> headerSpanArray = new SparseArray<>();     //用来记录每个分组最后一个item的span
    private GridLayoutManager.SpanSizeLookup lookup;

    @Override
    public void getItemOffsets(Rect outRect, View itemView, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, itemView, parent, state);
        final BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) parent.getAdapter();
        int position = parent.getChildAdapterPosition(itemView);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        Log.e(TAG, "设置span---------------------------- getItemOffsets ");

//        if (!adapter.isHeader(position) && !adapter.isFooter(position)) {
        if (lookup == null) {
            Log.e(TAG, "设置span----------------------------");

            lookup = new GridLayoutManager.SpanSizeLookup() {//相当于weight
                @Override
                public int getSpanSize(int position) {
                    Log.e(TAG, "设置span----------------------------："+position);

//                    if (adapter.isHeader(position) || adapter.isFooter(position)) {
//                        return 3;
//                    }
                    int returnSpan = 1;

                    int index = headerSpanArray.indexOfKey(position);
                    if (index >= 0) {
                        returnSpan = headerSpanArray.valueAt(headerSpanArray.indexOfKey(position));   //设置itemView PaddingTop的距离
                        Log.e(TAG, "设置span==" + position + "-span==" + returnSpan);
                    }

                    return returnSpan;
                }
            };
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            gridLayoutManager.setSpanSizeLookup(lookup);
        }


        /*我们为每个不同头部名称的第一个item设置头部高度*/
        int pos = parent.getChildAdapterPosition(itemView); //获取当前itemView的位置
        if (headerPaddingSet.contains(pos)) {
            outRect.top = 100;   //设置itemView PaddingTop的距离
            Log.w(TAG, "设置偏移==pos==" + pos + "-->" + outRect);
        }
//    }

    }


    private String getRealHeaderName(int pos) {
        if (pos >= itemTotalCount) {
            return "";
        }
        return getHeaderName(pos);
    }



    @Override
    public void onDestory() {
        super.onDestory();
        headerSpanArray.clear();
        headerPaddingSet.clear();
        groupHeadPos.clear();
    }
}
