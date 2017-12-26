package com.cn21.rnpreload;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cn21.rnpreloadlib.RNCacheViewManager;
import com.facebook.react.ReactRootView;

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
            restartApp();
        }
    }

    /**
     * 重启应用以使预加载生效
     */
    private void restartApp() {
        Intent mStartActivity = new Intent(this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
        System.exit(0);
    }

}
