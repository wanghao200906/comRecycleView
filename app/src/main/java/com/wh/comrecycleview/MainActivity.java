package com.wh.comrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button single_recyclerview;
    Button muliter_recyclerview;
    Button stickysection_recyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {


        single_recyclerview = (Button) findViewById(R.id.single_recyclerview);
        muliter_recyclerview = (Button) findViewById(R.id.muliter_recyclerview);
        stickysection_recyclerview = (Button) findViewById(R.id.stickysection_recyclerview);

        single_recyclerview.setOnClickListener(this);
        stickysection_recyclerview.setOnClickListener(this);
        muliter_recyclerview.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single_recyclerview:
                startActivity(new Intent(MainActivity.this, SingleRecyclerViewActivity.class));
                break;
            case R.id.muliter_recyclerview:
                startActivity(new Intent(MainActivity.this, MuliterRecyclerViewActivity.class));
                break;
            case R.id.stickysection_recyclerview:
                startActivity(new Intent(MainActivity.this, StickySectionActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
