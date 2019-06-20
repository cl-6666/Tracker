package com.cl.tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cl.tracker_cl.Tracker;

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
                Tracker.getInstance().addViewEvent(MainActivity.this, null, 0L);
            }
        });
    }


}
