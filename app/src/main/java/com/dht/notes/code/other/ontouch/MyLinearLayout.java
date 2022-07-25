package com.dht.notes.code.other.ontouch;

import android.content.Context;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by dai on 2018/4/12.
 */

public class MyLinearLayout extends LinearLayout {

    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //系统默认super.dispatchTouchEvent(ev) = true
        //返回true继续往下分发 若返回false onInterceptTouchEvent ，onTouchEvent将不会执行
        Log.d(TAG, "dispatchTouchEvent: "+ ev.getAction());
        boolean result = super.dispatchTouchEvent(ev);
//        Log.d(TAG, "dispatchTouchEvent: "+ ev.getAction() +" result = "+ result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: "+ ev.getAction());
        //系统默认super.onInterceptTouchEvent(ev) = false
        //返回true则对事件进行拦截，且onTouchEvent事件继续执行
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: "+ event.getAction());
        //系统默认onTouchEvent() = true
        //返回false 只影响自身的onClickListener事件
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
}
