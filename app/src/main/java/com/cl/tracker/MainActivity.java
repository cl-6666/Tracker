package com.cl.tracker;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cl.tracker_cl.Tracker;
import com.cl.tracker_cl.permissions.PermissionListener;
import com.cl.tracker_cl.permissions.PermissionsUtil;

public class MainActivity extends AppCompatActivity {


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //页面埋点
//        Tracker.getInstance().addViewEvent(MainActivity.this, null, 0L);

        button = findViewById(R.id.btn_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextActivity.enter(MainActivity.this, 1);
                Tracker.getInstance().setGPSLocation(1323.32,4232.32);   //测试经纬度
            }
        });


    }

}
