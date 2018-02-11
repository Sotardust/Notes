package com.dht.notes.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

/**
 * Created by dai on 2018/2/8.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThreadLocal  threadLocal = new ThreadLocal();
        HashMap hashMap = new HashMap();
    }
}
