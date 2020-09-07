package com.dht.notes.testcode.ontouch.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dai on 2018/4/2.
 */

public class MyView extends View {
    private static final String TAG = "MyView";


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //系统默认super.dispatchTouchEvent(event) =true
        //若返回false 将不再分发事件也不会执行onTouchEvent事件
        Log.d(TAG, "dispatchTouchEvent: "+ event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //系统默认super.onTouchEvent() = true
        //返回false 则不再执行自身点击事件
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
