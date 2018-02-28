package com.dht.notes.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dht.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/2/8.
 */

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.notes)
    MyTextView notes;
    @BindView(R.id.linearLayout)
    MyLinearLayout linearLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "linearLayout onClick() ");
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "notes onClick() ");
            }
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
//        return super.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(TAG, "dispatchTouchEvent() returned: " + super.dispatchTouchEvent(ev));
//        return super.dispatchTouchEvent(ev);
//    }
}
