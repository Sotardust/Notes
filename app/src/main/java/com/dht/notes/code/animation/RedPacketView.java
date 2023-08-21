package com.dht.notes.code.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dht.notes.R;

/**
 * created by dht on 2022/6/29 13:46
 */
public class RedPacketView extends View {

    private static final String TAG = "RedPacketView";

    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private Paint mPaint;

    private int currentWidth = 0;
    private int currentHeight = 0;

    private Point currentPoint;

    public RedPacketView(Context context) {
        super(context);
        initData();
    }

    public RedPacketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public RedPacketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_redpacket);
        bitmapWidth = 146;
        bitmapHeight = 168;
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        drawable.draw(canvas);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
//        Path path = new Path();
//        path.moveTo(600, 600);
//        path.quadTo(0, 300, 0, 600);
//        canvas.drawPath(path, null);
        Log.d(TAG, "onDraw drawable.bitmapWidth() = " + bitmapWidth);
        Log.d(TAG, "onDraw drawable.bitmapHeight() = " + bitmapHeight);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");

        if (currentPoint == null) {

            currentWidth = getMeasuredWidth() - bitmapWidth;

            currentHeight = getMeasuredHeight() - bitmapHeight;

            Point startPoint = new Point(currentWidth, currentHeight);

            canvas.drawBitmap(bitmap, startPoint.getX(), startPoint.getY(), null);

            Point endPoint = new Point(-bitmapWidth, -bitmapHeight);

            ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);

            anim.setInterpolator(new AccelerateDecelerateInterpolator());

            anim.setDuration(3000);

            anim.addUpdateListener(animation -> {
                currentPoint = (Point) animation.getAnimatedValue();

                System.out.println("MyView.onAnimationUpdate");
                invalidate();
            });

            anim.start();
        } else {
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            Log.d(TAG, "onDraw x =" + x + ", y = " + y);
            canvas.drawBitmap(bitmap, x, y, null);

        }

    }
}
