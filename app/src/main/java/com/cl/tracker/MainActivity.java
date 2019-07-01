package com.cl.tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cl.tracker_cl.Tracker;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * 全埋点
     *
     * @param view
     */
    public void onBtn1(View view) {
        AllBuriedActivity.enter(MainActivity.this, 1);
    }


    /**
     * 普通埋点
     *
     * @param view
     */
    public void onBtn2(View view) {
        OrdinaryActivity.enter(MainActivity.this, 1);
    }


    /**
     * 页面埋点
     *
     * @param view
     */
    public void onBtn3(View view) {
        PageActivity.enter(MainActivity.this, 1);
    }


    /**
     * 经纬度测试
     *
     * @param view
     */
    public void onBtn4(View view) {
        showToast("插入经纬度成功" + "1323.32" + "---" + "4232.32");
        Tracker.getInstance().setGPSLocation(1323.32, 4232.32);   //测试经纬度
    }


    public void onBtn5(View view) {
        showToast("登录成功:" + "9771C579-71F0-4650-8EE8-8999FA717761");
        Tracker.getInstance().getDistinctId("9771C579-71F0-4650-8EE8-8999FA717761");
    }

    public void onBtn6(View view) {
        showToast("退出成功");
        Tracker.getInstance().getDistinctId("");
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
