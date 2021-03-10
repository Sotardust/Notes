package com.dht.notes.code.other.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dht.notes.R;

import java.util.HashMap;
import java.util.Map;

/**
 * created by Administrator on 2019/10/11 11:05
 */
public class ServiceActivity extends Activity {

    private static final String TAG = "dht";
    MyService.MyBinder myBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button button1 = findViewById(R.id.btn1);
        Button button2 = findViewById(R.id.btn2);

        Button button3 = findViewById(R.id.btn3);
        Button button4 = findViewById(R.id.btn4);
        Button button5 = findViewById(R.id.btn5);

//        HashMap<String,Integer> hashMap = new HashMap<>();
//        hashMap.put("111",1);
//        hashMap.put("8888",2);
//        hashMap.put("333",3);
//        hashMap.put("444",4);
//        hashMap.put("5555",5);
//        hashMap.put("6666",6);
//        hashMap.put("77771",7);
//        hashMap.put("77772",7);
//        hashMap.put("77737",7);
//        hashMap.put("77747",7);
//        hashMap.put("77577",7);
//        hashMap.put("77677",7);
//        hashMap.put("7177177",7);
//        hashMap.put("777177",7);
//        hashMap.put("77877",7);
//        hashMap.put("7729077",7);
//        hashMap.put("779077",7);
//        hashMap.put("77390177",7);
//        hashMap.put("7739077",7);
//        hashMap.put("77930177",7);
//        hashMap.put("77932077",7);
//        hashMap.put("77943077",7);
//        hashMap.put("4",7);
//        hashMap.put("77934077",7);
//        hashMap.put("77935077",7);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("语文", 1);
        map.put("数学", 2);
        map.put("英语", 3);
        map.put("历史", 4);
        map.put("政治", 5);
        map.put("地理", 6);
        map.put("生物", 7);
        map.put("化学", 8);
//        for(Map.Entry<String, Integer> entry : hashMap.entrySet()) {
//            Log.d(TAG, "onCreate()  key = [" + entry.getKey() + ": " + entry.getValue() + "]");
//        }

        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            Log.d(TAG, "onCreate()  key = [" + entry.getKey() + ": " + entry.getValue() + "]");
        }

        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = ((MyService.MyBinder) service);

                Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
            }
        };
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                unbindService(connection);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.testData();
            }
        });

    }
}
