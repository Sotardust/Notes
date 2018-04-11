package com.dht.notes.testcode.ontouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyTextView extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
        Log.d(TAG, "MyTextView() called with: context = [" + context + "]");
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MyTextView() called with: context = [" + context + "], attrs = [" + attrs + "]");
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "MyTextView() called with: context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

//        Log.i(TAG, "dispatchTouchEvent() returned: " + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

}
