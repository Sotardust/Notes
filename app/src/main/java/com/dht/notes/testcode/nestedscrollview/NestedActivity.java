package com.dht.notes.testcode.nestedscrollview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dht.notes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestedActivity extends Activity {

    private static final String TAG = "dht";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);

        RecyclerView recyclerView = findViewById(R.id.recycle);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        List<String> list = new ArrayList<>(5);
        for (int i = 0; i < 8; i++) {
            list.add("数据" + i);
        }


        List<String> list1 = list.subList(0, 3);

//        list.remove("数据");
        list1.add("dfas");
        list1.remove("数据1");
        Log.d(TAG, "onCreate() called with: list = [" + list + "]");
        Log.d(TAG, "onCreate() called with: lis1=t = [" + list1 + "]");
        recyclerView.setNestedScrollingEnabled(false);
        NestedAdapter nestedAdapter = new NestedAdapter(list);

        recyclerView.setAdapter(nestedAdapter);

        HashMap<String, Integer> hashMap = new HashMap<>(8);

        hashMap.put("Java", 1);
        hashMap.put("Kotlin", 2);
        hashMap.put("Android", 3);
        hashMap.put("Flutter", 4);
        hashMap.put("Python", 5);
        hashMap.put("C", 6);
        hashMap.put("C++", 7);
        hashMap.put("PHP", 8);
        hashMap.put("Objective-C", 9);
        hashMap.put("JavaScript", 10);
        hashMap.put("Mysql", 11);
        hashMap.put("Swift", 12);
        hashMap.put("Go", 13);

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            Log.d(TAG, "HashMap  = [" + entry.getKey() + " -> " + entry.getValue() + "]");
        }
    }
}
