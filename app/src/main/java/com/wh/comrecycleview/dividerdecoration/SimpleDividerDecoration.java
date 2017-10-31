package com.wh.comrecycleview.dividerdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wh.comrecycleview.R;
import com.recycleView.BaseRecyclerViewAdapter;

public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SimpleDividerDecoration";
    private int dividerHeight;
    private Paint dividerPaint;

    public SimpleDividerDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        dividerHeight = 20;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.d(TAG, "getItemOffsets: ");
        RecyclerView.Adapter adapter = parent.getAdapter();

        BaseRecyclerViewAdapter newAdapter;
        if (adapter instanceof BaseRecyclerViewAdapter) {
            newAdapter = (BaseRecyclerViewAdapter) adapter;
        } else {
            throw new RuntimeException("the adapter must be BaseRecyclerViewAdapter");
        }

        int position = parent.getChildAdapterPosition(view);
        if (newAdapter.isHeader(position) || newAdapter.isFooter(position)) {
            outRect.set(0, 0, 0, 0);
        } else {

            outRect.set(0, 0, 0, dividerHeight);

        }

    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.d(TAG, "onDrawOver: ");
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.d(TAG, "onDraw: ");
        RecyclerView.Adapter adapter = parent.getAdapter();

        BaseRecyclerViewAdapter newAdapter;
        if (adapter instanceof BaseRecyclerViewAdapter) {
            newAdapter = (BaseRecyclerViewAdapter) adapter;
        } else {
            throw new RuntimeException("the adapter must be BaseRecyclerViewAdapter");
        }


        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);

            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            int position = parent.getChildAdapterPosition(view);
            c.save();
            if (newAdapter.isHeader(position) || newAdapter.isFooter(position)) {
                c.drawRect(0, 0, 0, 0, dividerPaint);
            } else {

                Log.d(TAG, "onDraw1 : ");

                c.drawRect(left, top, right, bottom, dividerPaint);
            }
            c.restore();
        }
    }


}