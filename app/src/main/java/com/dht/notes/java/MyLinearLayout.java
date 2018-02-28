package com.dht.notes.java;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyLinearLayout extends LinearLayoutCompat {
    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent() returned: " + super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent() returned: " + super.onInterceptTouchEvent(ev));
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
