package com.dht.notes.testcode.ontouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyTextView extends AppCompatTextView {
    private static final String TAG = "MyTextView";

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "MyTextView: ");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Log.i(TAG, "dispatchTouchEvent() returned: " + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        Log.i(TAG, "setOnClickListener: ");
        super.setOnClickListener(l);
    }
}
