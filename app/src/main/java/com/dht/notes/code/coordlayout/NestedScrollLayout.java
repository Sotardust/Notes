package com.dht.notes.code.coordlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.dht.notes.R;

public class NestedScrollLayout extends NestedScrollView {

    private static final String TAG = "dht1";

    public NestedScrollLayout(@NonNull Context context) {
        super(context);
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean canScrollVertically(int direction) {

        Log.d(TAG, "canScrollVertically: ");
        return super.canScrollVertically(direction);
    }

    boolean isDispatch = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        Log.d(TAG, "dispatchTouchEvent: ev.getY()" + ev.getY()  +" isDispatch = "+ isDispatch);
        if (isDispatch) {
            return super.dispatchTouchEvent(ev);
        }

        return false;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        RelativeLayout headView = this.findViewById(R.id.rl_head);
        RelativeLayout content = this.findViewById(R.id.rl_content);

        Log.d(TAG, "onScrollChanged() called with: headView.getHeight() = [" + headView.getHeight() + "], t = [" + t + "], oldl = [" + oldl + "], oldt = [" + oldt + "]");

        if (oldl > headView.getHeight()) {
            // super.onScrollChanged(l, t, oldl, oldt);
            isDispatch = false;
            return;
        }
        super.onScrollChanged(l, t, oldl, oldt);
        isDispatch = true;


    }
}
