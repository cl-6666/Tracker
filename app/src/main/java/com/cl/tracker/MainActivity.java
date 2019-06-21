package com.cl.tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cl.tracker_cl.Tracker;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //页面埋点
//        Tracker.getInstance().addViewEvent(MainActivity.this, null, 0L);

//        button1 = findViewById(R.id.btn_text);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextActivity.enter(MainActivity.this, 1);
//                Tracker.getInstance().setGPSLocation(1323.32, 4232.32);   //测试经纬度
//            }
//        });


    }


    /**
     * 无参事件
     *
     * @param view
     */
    public void onBtn1(View view) {
        Tracker.getInstance().addClickEvent(view);
    }

    /**
     * 有参事件
     *
     * @param view
     */
    public void onBtn2(View view) {

    }

    /**
     * 登录id测试（保存id）
     *
     * @param view
     */
    public void onBtn3(View view) {
        Tracker.getInstance().getDistinctId("9771C579-71F0-4650-8EE8-8999FA717761");
    }


    /**
     * 退出登录（清空id）
     *
     * @param view
     */
    public void onBtn4(View view) {
        Tracker.getInstance().getDistinctId("");
    }

    /**
     * 经纬度测试
     *
     * @param view
     */
    public void onBtn5(View view) {
        Tracker.getInstance().setGPSLocation(1323.32, 4232.32);   //测试经纬度
    }

    public void onBtn6(View view) {


    }

    public void onBtn7(View view) {


    }

    /**
     * 页面事件(进入和退出)
     *
     * @param view
     */
    public void onBtn8(View view) {


    }


}
