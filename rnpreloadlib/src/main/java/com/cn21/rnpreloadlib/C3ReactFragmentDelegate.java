package com.cn21.rnpreloadlib;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

/**
 * RN Fragment代理
 * Created by lizhj on 2017/7/5.
 */

public class C3ReactFragmentDelegate extends ReactActivityDelegate {

    public C3ReactFragmentDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Class<ReactActivityDelegate> clazz = ReactActivityDelegate.class;
        try {
            Field field = clazz.getDeclaredField("mDoubleTapReloadRecognizer");
            field.setAccessible(true);
            field.set(this, new DoubleTapReloadRecognizer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected ReactNativeHost getReactNativeHost() {
        return super.getReactNativeHost();
    }


}
