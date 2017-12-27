package com.cn21.rnpreloadlib;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewParent;
import android.widget.Toast;

import com.facebook.common.logging.FLog;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.ReactConstants;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 缓存view管理
 * Created by lizhj on 2017/6/26.
 */

public class RNCacheViewManager {
    public Map<String, ReactRootView> CACHE;
    public static final int REQUEST_OVERLAY_PERMISSION_CODE = 1111;
    public static final String REDBOX_PERMISSION_MESSAGE =
            "Overlay permissions needs to be granted in order for react native apps to run in dev mode";

    private RNCacheViewManager() {
    }

    private static class SingletonHolder {
        private final static RNCacheViewManager INSTANCE = new RNCacheViewManager();
    }

    public static RNCacheViewManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ReactRootView getRootView(String moduleName) {
        if (CACHE == null) return null;
        return CACHE.get(moduleName);
    }

    public ReactNativeHost getReactNativeHost(Activity activity) {
        return ((ReactApplication) activity.getApplication()).getReactNativeHost();
    }

    /**
     * 预加载所需的RN模块
     *
     * @param activity      预加载时所在的Activity
     * @param launchOptions 启动参数
     * @param moduleNames   预加载模块名
     *                      建议在主界面onCreate方法调用，最好的情况是主界面在应用运行期间一直存在不被关闭
     */
    public void init(Activity activity, Bundle launchOptions, String... moduleNames) {
        if (CACHE == null) CACHE = new WeakHashMap<>();
        boolean needsOverlayPermission = false;
        if (getReactNativeHost(activity).getUseDeveloperSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            needsOverlayPermission = true;
            Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            FLog.w(ReactConstants.TAG, REDBOX_PERMISSION_MESSAGE);
            Toast.makeText(activity, REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show();
            activity.startActivityForResult(serviceIntent, REQUEST_OVERLAY_PERMISSION_CODE);
        }

        if (!needsOverlayPermission) {
            for (String moduleName : moduleNames) {
                ReactRootView rootView = new ReactRootView(activity);
                rootView.startReactApplication(
                        getReactNativeHost(activity).getReactInstanceManager(),
                        moduleName,
                        launchOptions);
                CACHE.put(moduleName, rootView);
                FLog.i(ReactConstants.TAG, moduleName + " has preload");
            }
        }
    }

    /**
     * 销毁指定的预加载RN模块
     *
     * @param componentName
     */
    public void onDestroyOne(String componentName) {
        try {
            ReactRootView reactRootView = CACHE.get(componentName);
            if (reactRootView != null) {
                ViewParent parent = reactRootView.getParent();
                if (parent != null) {
                    ((android.view.ViewGroup) parent).removeView(reactRootView);
                }
                reactRootView.unmountReactApplication();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁全部RN模块
     * 建议在主界面onDestroy方法调用
     */
    public void onDestroy() {
        try {
            for (Map.Entry<String, ReactRootView> entry : CACHE.entrySet()) {
                ReactRootView reactRootView = entry.getValue();
                ViewParent parent = reactRootView.getParent();
                if (parent != null) {
                    ((android.view.ViewGroup) parent).removeView(reactRootView);
                }
                reactRootView.unmountReactApplication();
                reactRootView = null;
            }
            CACHE.clear();
            CACHE = null;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
