package com.dht.notes.code.animation;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;

import com.dht.notes.R;

public class RadarView extends FrameLayout {

    private Context mContext;
    private int viewSize = 800;
    private Paint mPaintLine;
    private Paint mPaintSector;
    //旋转效果起始角度
    private int start = 0;

    private int[] point_x;
    private int[] point_y;

    private Shader mShader;

    private Matrix matrix;

    public final static int CLOCK_WISE = 1;
    public final static int ANTI_CLOCK_WISE = -1;

    @IntDef({CLOCK_WISE, ANTI_CLOCK_WISE})
    public @interface RADAR_DIRECTION {

    }

    //默认为顺时针呢
    private final static int DEFAULT_DIERCTION = CLOCK_WISE;

    //设定雷达扫描方向
    private int direction = DEFAULT_DIERCTION;

    private boolean threadRunning = true;

    public RadarView (Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        initPaint();
    }

    public RadarView (Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        initPaint();

    }

    private void initPaint () {

        setBackgroundColor(Color.TRANSPARENT);

        //宽度=5，抗锯齿，描边效果的白色画笔
        mPaintLine = new Paint();
        mPaintLine.setStrokeWidth(20);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setColor(Color.WHITE);
        mShader = new SweepGradient(viewSize / 2, viewSize / 2, Color.TRANSPARENT, Color.WHITE);
        mPaintLine.setShader(mShader);

        //暗绿色的画笔
        mPaintSector = new Paint();
        mPaintSector.setColor(Color.WHITE);
        mPaintSector.setAntiAlias(true);
        mShader = new SweepGradient(viewSize / 2, viewSize / 2, Color.TRANSPARENT, getContext().getResources().getColor(R.color.color_595ecdfb));
        mPaintSector.setShader(mShader);

    }

    public void setViewSize (int size) {
        this.viewSize = size;
        setMeasuredDimension(viewSize, viewSize);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        setMeasuredDimension(viewSize, viewSize);
    }


    @Override
    protected void onDraw (Canvas canvas) {
        canvas.drawCircle(viewSize / 2, viewSize / 2, 350, mPaintLine);
        canvas.drawCircle(viewSize / 2, viewSize / 2, 340, mPaintSector);
        super.onDraw(canvas);
    }

}

