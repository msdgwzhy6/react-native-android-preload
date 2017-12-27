package com.cn21.rnpreload;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lizhj on 2017/12/26.
 */

public class MyFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Fragment myFragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, myFragment).commit();
    }
}
