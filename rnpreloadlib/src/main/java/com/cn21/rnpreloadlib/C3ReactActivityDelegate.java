package com.cn21.rnpreloadlib;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.facebook.common.logging.FLog;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

/**
 * RN承载的Activity代理
 *
 * Created by lizhj on 2017/6/26.
 */

public class C3ReactActivityDelegate extends ReactActivityDelegate {
    private Activity mActivity;
    private String mainComponentName;

    public C3ReactActivityDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
        this.mActivity = activity;
        this.mainComponentName = mainComponentName;
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

        loadApp(null);
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

    @Override
    protected void loadApp(String appKey) {
        if (mainComponentName == null) {
            FLog.e(ReactConstants.TAG, "mainComponentName must not be null!");
            return;
        }
        ReactRootView reactRootView = RNCacheViewManager.getRootView(mainComponentName);
        try {
            if (reactRootView != null) {
                ViewParent viewParent = reactRootView.getParent();
                if (viewParent != null) {
                    ViewGroup vp = (ViewGroup) viewParent;
                    vp.removeView(reactRootView);
                }
                mActivity.setContentView(reactRootView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
