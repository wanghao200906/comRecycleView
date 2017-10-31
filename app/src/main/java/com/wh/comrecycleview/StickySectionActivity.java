package com.wh.comrecycleview;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wh.comrecycleview.dividerdecoration.GridDecoration;
import com.wh.comrecycleview.dividerdecoration.GridStickySectionDecoration;
import com.wh.comrecycleview.entitiy.UserName;
import com.recycleView.SimpleAdapter;
import com.recycleView.ViewHolder;
import com.recycleView.click.ItemClickListener;
import com.recycleView.click.ItemLongClickListener;
import com.recycleView.click.ViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghao on 2017/10/25.
 */

public class StickySectionActivity extends AppCompatActivity {
    private static final String TAG = "SingleRecyclerViewActiv";
    RecyclerView recyclerview;
    List<UserName> data = new ArrayList<>();
    SimpleAdapter singlerRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recyclerview);
        init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void init() {

        getData();
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(gridLayoutManager);

        recyclerview.addItemDecoration(new GridStickySectionDecoration(data.size(), 2) {
            @Override
            public String getHeaderName(int pos) {
                return data.get(pos).getNameIndex();
            }
        });
        recyclerview.addItemDecoration(new GridDecoration(10, 10, getResources().getColor(android.R.color.background_light)));


//        LinearStickySectionDecoration normalDecoration = new LinearStickySectionDecoration() {
//            @Override
//            public String getHeaderName(int pos) {
//                return data.get(pos).getNameIndex();
//            }
//        };
//        recyclerview.addItemDecoration(normalDecoration);






        singlerRecyclerViewAdapter = new SimpleAdapter<UserName>(R.layout.view_rv_item, data) {
            @Override
            protected void onBindView(ViewHolder holder, UserName s, int position) {
                TextView tv = holder.getView(R.id.item_tv);
                tv.setText("s:" + s.getName());
            }
        };


        recyclerview.setAdapter(singlerRecyclerViewAdapter);

        singlerRecyclerViewAdapter.setOnViewClickListener(R.id.root, new ViewClickListener() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        Log.d(TAG, "setOnViewClickListener item_tv: " + position);
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
        UserName userName = new UserName();
        data.addAll(userName.getData());
    }
}
