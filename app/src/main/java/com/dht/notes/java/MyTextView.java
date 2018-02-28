package com.dht.notes.java;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyTextView extends AppCompatTextView {
    private static final String TAG = "MyTextView";

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("MyTextView.MyTextView");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Log.i(TAG, "dispatchTouchEvent() returned: " + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent() returned: " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

}
