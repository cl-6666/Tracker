package com.cl.tracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cl.tracker_cl.Tracker;
import com.cl.tracker_cl.bean.SensorsDataDynamicSuperProperties;

import org.json.JSONException;
import org.json.JSONObject;

public class OrdinaryActivity extends AppCompatActivity {


    public static void enter(Context context, int style) {
        Intent intent = new Intent(context, OrdinaryActivity.class);
        intent.putExtra("style", style);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary);

        //设置动态公共属性
        Tracker.getInstance().registerDynamicSuperProperties(new SensorsDataDynamicSuperProperties() {
            @Override
            public JSONObject getDynamicSuperProperties() {
                try {
                    return new JSONObject().put("isLogin", "反反复复付付付付付付");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public void onBtn1(View view) throws JSONException {
        JSONObject properties = new JSONObject();
        properties.put("ProductID", 123456);                    // 设置商品ID
        properties.put("ProductCatalog", "Laptop Computer");    // 设置商品类别
        properties.put("IsAddedToFav", false);
        Tracker.getInstance().getTitle(this).track("Button", properties);

    }

    public void onBtn2(View view) {


    }

    public void onBtn3(View view) throws JSONException {
        JSONObject properties = new JSONObject();
        properties.put("ProductID", 123456);                    // 设置商品ID
        properties.put("ProductCatalog", "Laptop Computer");    // 设置商品类别
        properties.put("IsAddedToFav", false);
        // 是否被添加到收藏夹
        Tracker.getInstance().track("TextView", properties);
    }
}
