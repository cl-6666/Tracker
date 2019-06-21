package com.cl.tracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cl.tracker_cl.Tracker;
import com.cl.tracker_cl.listener.ViewClickedEventListener;

public class TextActivity extends AppCompatActivity {


    private Button mButton1, mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        mButton1 = findViewById(R.id.btn_fh);
        mButton2 = findViewById(R.id.btn_sj);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewClickedEventListener.getInstance().setActivityTracker(TextActivity.this);
            }
        });
    }


    public static void enter(Context context, int style) {
        Intent intent = new Intent(context, TextActivity.class);
        intent.putExtra("style", style);
        context.startActivity(intent);
    }
}
