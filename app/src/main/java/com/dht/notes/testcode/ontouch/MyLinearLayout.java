package com.dht.notes.testcode.ontouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
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
        Log.i(TAG, "MyLinearLayout: ");
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        Log.i(TAG, "setOnClickListener: ");
    }
}
