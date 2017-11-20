package com.cn21.rnpreload;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cn21.rnpreloadlib.RNCacheViewManager;

import static com.cn21.rnpreloadlib.RNCacheViewManager.REQUEST_OVERLAY_PERMISSION_CODE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进行预加载
        RNCacheViewManager.init(this, null, PreLoadRNActivity.COMPONENT_NAME, PreLoadRNFragment.COMPONENT_NAME);
    }

    public void gotoRNActivity(View view) {
        startActivity(new Intent(this, PreLoadRNActivity.class));
    }

    public void gotoRNFragment(View view) {
        startActivity(new Intent(this, PreloadRNFragmentActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity onDestroy");
        //销毁预加载的RN模块
        RNCacheViewManager.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //处理调试模式下悬浮窗权限被授予回调
        if (requestCode == REQUEST_OVERLAY_PERMISSION_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "预加载已生效，请重新打开应用！", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
