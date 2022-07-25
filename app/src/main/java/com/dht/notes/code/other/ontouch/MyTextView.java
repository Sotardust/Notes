package com.dht.notes.code.other.ontouch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MyTextView extends ViewGroup {
    private static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
//        Log.d(TAG, "MyTextView() called with: context = [" + context + "]");
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Log.d(TAG, "MyTextView() called with: context = [" + context + "], attrs = [" + attrs + "]");
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        Log.d(TAG, "MyTextView() called with: context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        requestDisallowInterceptTouchEvent(false);
        Log.d(TAG, "onInterceptTouchEvent() called with: ev = [" + ev.getAction() + "]");
        return super.onInterceptTouchEvent(ev);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d(TAG, "onMeasure: ");
    }



    @Override
    protected void onDraw(Canvas canvas) {
//        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
}
