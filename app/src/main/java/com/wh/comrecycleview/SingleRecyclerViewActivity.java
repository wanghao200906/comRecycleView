package com.wh.comrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wh.comrecycleview.dividerdecoration.SimpleDividerDecoration;
import com.recycleView.SimpleAdapter;
import com.recycleView.ViewHolder;
import com.recycleView.click.ItemClickListener;
import com.recycleView.click.ItemLongClickListener;
import com.recycleView.click.OnLoadMoreListener;
import com.recycleView.click.ViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghao on 2017/9/29.
 */

public class SingleRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "SingleRecyclerViewActiv";
    RecyclerView recyclerview;
    List<String> data = new ArrayList<>();
    SimpleAdapter singlerRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recyclerview);
        init();
    }

    private void init() {

        getData();
        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        Button t2 = new Button(this);
        t2.setText("Header 2");
        TextView t3 = new TextView(this);
        t3.setText("Header 3");
        TextView t4 = new TextView(this);
        t4.setText("Footer 4");
        ProgressBar progress = new ProgressBar(this);


        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        singlerRecyclerViewAdapter = new SimpleAdapter<String>(R.layout.view_rv_item, data) {
            @Override
            protected void onBindView(ViewHolder holder, String s, int position) {
                TextView tv = holder.getView(R.id.item_tv);
                tv.setText("s:" + s);
            }
        };

        singlerRecyclerViewAdapter.addHeaderView(t1);
        singlerRecyclerViewAdapter.addHeaderView(t2);
        singlerRecyclerViewAdapter.addHeaderView(t3);
        singlerRecyclerViewAdapter.addFooterView(t4);
        singlerRecyclerViewAdapter.setLoadMoreView(progress);

        recyclerview.setAdapter(singlerRecyclerViewAdapter);
        recyclerview.addItemDecoration(new SimpleDividerDecoration(this));
        singlerRecyclerViewAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.d(TAG, "onLoadMoreRequested: " + data.size());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < 20; i++) {
                            data.add("位置：" + i);
                        }
                        singlerRecyclerViewAdapter.notifyDataSetChanged();
                        Log.d(TAG, "run: " + data.size());
                    }
                }, 300);
            }
        });

        singlerRecyclerViewAdapter.setOnViewClickListener(R.id.root, new ViewClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        Log.d(TAG, "setOnViewClickListener item_tv: " + position);
//                        singlerRecyclerViewAdapter.removeItem(position);
//                        singlerRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
        );

        singlerRecyclerViewAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d(TAG, "onItemClicked: " + position);
                singlerRecyclerViewAdapter.removeItem(position);
                singlerRecyclerViewAdapter.notifyDataSetChanged();

            }
        });

        singlerRecyclerViewAdapter.setOnItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Log.d(TAG, "onItemLongClicked: " + position);

            }
        });
    }

    private void getData() {
        for (int i = 0; i < 30; i++) {
            data.add("位置：" + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_linear:
                recyclerview.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_grid:
                recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.action_staggered:
                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        recyclerview.setAdapter(singlerRecyclerViewAdapter);
        return super.onOptionsItemSelected(item);
    }
}
