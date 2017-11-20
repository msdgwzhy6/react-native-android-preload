package com.cn21.rnpreload;

import com.cn21.rnpreloadlib.C3ReactAppCompatActivity;
import com.squareup.leakcanary.RefWatcher;

/**
 * 预加载Activity
 * Created by lizhj on 2017/11/16.
 */

public class PreLoadRNActivity extends C3ReactAppCompatActivity  {
    public static final String COMPONENT_NAME=PreLoadRNActivity.class.getSimpleName();

    @Override
    public String getMainComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
