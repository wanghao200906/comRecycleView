package com.wh.comrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.recycleView.click.ItemClickListener;
import com.recycleView.click.ItemLongClickListener;
import com.recycleView.click.OnLoadMoreListener;
import com.recycleView.click.ViewClickListener;
import com.wh.comrecycleview.multi.MultiAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghao on 2017/10/24.
 */

public class MuliterRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "SingleRecyclerViewActiv";
    RecyclerView recyclerview;
    List<String> data = new ArrayList<>();
    MultiAdapter mulitAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recyclerview);
        init();
    }

    private void init() {

        getData();

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mulitAdaper = new MultiAdapter(data);

        View header1 = LayoutInflater.from(this).inflate(R.layout.header, recyclerview, false);
        View header2 = LayoutInflater.from(this).inflate(R.layout.header2, recyclerview, false);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, recyclerview, false);
        View loadmore = LayoutInflater.from(this).inflate(R.layout.loadmore, recyclerview, false);

//        mulitAdaper.addHeaderView(header1);
//        mulitAdaper.addHeaderView(header2);
//        mulitAdaper.addFooterView(footer);
//        mulitAdaper.setLoadMoreView(loadmore);

        recyclerview.setAdapter(mulitAdaper);
        mulitAdaper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.d(TAG, "onLoadMoreRequested: " + data.size());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < 20; i++) {
                            data.add("位置：" + i);
                        }
                        mulitAdaper.notifyDataSetChanged();
                        Log.d(TAG, "run: " + data.size());
                    }
                }, 1000);
            }
        });

        mulitAdaper.setOnViewClickListener(R.id.root, new ViewClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        Log.d(TAG, "setOnViewClickListener item_tv: " + position);
//                        mulitAdaper.removeItem(position);
//                        mulitAdaper.notifyDataSetChanged();
                    }
                }
        );

        mulitAdaper.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d(TAG, "onItemClicked: " + position);
                mulitAdaper.removeItem(position);
                mulitAdaper.notifyDataSetChanged();

            }
        });

        mulitAdaper.setOnItemLongClickListener(new ItemLongClickListener() {
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
        recyclerview.setAdapter(mulitAdaper);
        return super.onOptionsItemSelected(item);
    }
}
