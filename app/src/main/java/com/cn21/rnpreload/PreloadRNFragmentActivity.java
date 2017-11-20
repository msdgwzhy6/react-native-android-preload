package com.cn21.rnpreload;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cn21.rnpreloadlib.C3ReactFragmentActivity;
import com.squareup.leakcanary.RefWatcher;

/**
 * 预加载Fragment的Activity
 * Created by lizhj on 2017/11/16.
 */

public class PreloadRNFragmentActivity extends C3ReactFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Fragment exampleFragment = new PreLoadRNFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, exampleFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
