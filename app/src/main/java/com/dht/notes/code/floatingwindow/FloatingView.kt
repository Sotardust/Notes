package com.dht.notes.code.floatingwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.dht.notes.R
import com.dht.notes.code.animation.WifiWaveView
import kotlin.math.abs

/**
 * created by dht on 2021/8/18 15:33
 */
class FloatingView : View {

    //内圈小圆的圆心
    private var innerCircleX = 0f
    private var innerCircleY = 0f

    //内圈小圆的半径
    private var innerCircleRadius = 0f

    //View的宽高
    private var mWidth = 0
    private var mHeight = 0

    //圆环宽度
    private var mStrokeWidth = 20f

    //画笔
    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.FILL
            strokeWidth = 10f
        }
    }

    //画笔
    private val mPaint1 by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 10f
        }
    }

    constructor(context: Context?) : super(context) {
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initData()
    }

    private var sweepAngle = 270f
    private var lastSweepAngle = 0f
    private fun initData() {
        ValueAnimator.ofInt(-90, -450).apply {
            this.interpolator = LinearInterpolator()
            this.duration = 5000
            this.start()
            this.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {

                }
            })
            addUpdateListener { animation ->
                sweepAngle = (animation.animatedValue as Int).toFloat()
                lastSweepAngle = abs(90f + ((animation.animatedValue as Int)))
                Log.d(TAG, "onAnimationUpdate: sweepAngle $sweepAngle  value =${animation.animatedValue as Int}")
                invalidate()
            }
        }
    }

    //扇形所在的矩形区域
    private val rectF = RectF()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mWidth =500 //设置默认宽高
        }
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mHeight = 500
        }
        //外圈扇形所在矩形区域
        rectF.set(paddingLeft.toFloat(), paddingTop.toFloat(), mWidth.toFloat() - paddingRight.toFloat(), mHeight.toFloat() - paddingBottom.toFloat())
        innerCircleX = mWidth * 0.5f
        innerCircleY = mHeight * 0.5f
        innerCircleRadius = mWidth * 0.5f - mStrokeWidth - (paddingStart + paddingEnd)
        //保存测量结果
        setMeasuredDimension(mWidth, mHeight)

        Log.d(TAG, "onMeasure() called with: innerCircleX = $innerCircleX, innerCircleY = $innerCircleY innerCircleRadius = $innerCircleRadius")
    }

    var isFirst = true
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isFirst) {
            isFirst = false
            val color1 = ContextCompat.getColor(context, R.color.color_faee65)
            val color2 = ContextCompat.getColor(context, R.color.color_eeaf47)
            val shader = SweepGradient(rectF.width() / 2f, rectF.height() / 2f, color2, color1)
            val matrix = Matrix()
            matrix.postRotate(-90f,rectF.width() / 2f, rectF.height() / 2f)
            shader.setLocalMatrix(matrix)
            mPaint1.shader = shader

        }
        // canvas.drawArc(rectF, 30f, 90f, true, mPaint1)
        // for (entry in 0..32) {
        //     val sweepAngle = entry.value / valueAll * 360f
        //     startAngle += sweepAngle
        // }
        Log.d(TAG, "onDraw: sweepAngle = $sweepAngle lastSweepAngle = $lastSweepAngle")
        // canvas.drawArc(rectF,  -sweepAngle, -lastSweepAngle,true, mPaint1)
        canvas.drawArc(rectF, sweepAngle, lastSweepAngle, true, mPaint1)
        // lastSweepAngle = sweepAngle

        canvas.drawCircle(innerCircleX, innerCircleY, innerCircleRadius, mPaint)
    }

    companion object {
        private const val TAG = "dht"
    }
}