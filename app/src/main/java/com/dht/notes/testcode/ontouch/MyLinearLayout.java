package com.dht.notes.testcode.ontouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyLinearLayout extends LinearLayout {
    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
        Log.d(TAG, "MyLinearLayout() called with: context = [" + context + "]");
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MyLinearLayout() called with: context = [" + context + "], attrs = [" + attrs + "]");
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "MyLinearLayout() called with: context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(TAG, "dispatchTouchEvent() returned: " + super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i(TAG, "onInterceptTouchEvent() returned: " + super.onInterceptTouchEvent(ev));
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

}
