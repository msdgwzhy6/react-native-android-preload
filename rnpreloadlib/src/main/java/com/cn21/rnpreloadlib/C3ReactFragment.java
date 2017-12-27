package com.cn21.rnpreloadlib;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactRootView;

/**
 * 将Fragment继承本类将会预加载RN模块,同时装载Fragment的Activity也需要继承C3ReactFragmentActivity
 * Created by lizhj on 2017/7/4.
 */

public abstract class C3ReactFragment extends Fragment {
    private ReactRootView mReactRootView;
    public abstract String getMainComponentName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReactRootView = RNCacheViewManager.getInstance().getRootView(getMainComponentName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mReactRootView;
    }

}
