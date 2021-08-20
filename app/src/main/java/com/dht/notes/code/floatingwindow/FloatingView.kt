package com.dht.notes.code.floatingwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
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

/**
 * created by dht on 2021/8/18 15:33
 */
class FloatingView : View {


    //内圈小圆的圆心
    private var circleX = 0f
    private var circleY = 0f

    //内圈小圆的半径
    private var innerCircleRadius = 0f

    //外圈大圆的半径
    private var circleRadius = 0f

    //View的宽高
    private var viewWidth = 0
    private var viewHeight = 0

    //圆环宽度
    private var strokeWidth = 15f

    private val bgPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.color_6F6F70)
            style = Paint.Style.FILL
            strokeWidth = 5f
        }
    }

    private val gradientPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 5f
        }
    }
    private val grayPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.color_8A8A8A)
            style = Paint.Style.FILL
            strokeWidth = 5f
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

    private var currentIndex = 0
    private var lastSweepAngle = 0f
    private fun initData() {

        // ValueAnimator.ofInt(-90, -360).apply {
        ValueAnimator.ofInt(0, 30).apply {
            this.interpolator = LinearInterpolator()
            this.duration = 1000
            this.start()
            this.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {

                }
            })
            addUpdateListener { animation ->
                currentIndex = animation.animatedValue as Int
                invalidate()
            }
        }
    }

    //扇形所在的矩形区域
    private val rectF = RectF()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        Log.d(TAG, "onMeasure() called with: viewWidth = $viewWidth, viewHeight = $viewHeight")
        if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            viewWidth = 220 //设置默认宽高
        }
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            viewHeight = 220
        }
        //外圈扇形所在矩形区域
        rectF.set(paddingLeft.toFloat(), paddingTop.toFloat(), viewWidth.toFloat() - paddingRight.toFloat() - 2 * strokeWidth, viewHeight.toFloat() - paddingBottom.toFloat() - 2 * strokeWidth)
        circleX = viewWidth / 2f
        circleY = viewHeight / 2f
        circleRadius = viewWidth / 2f
        innerCircleRadius = viewWidth / 2f - strokeWidth - (paddingStart + paddingEnd)
        //保存测量结果
        setMeasuredDimension((viewWidth), (viewHeight))
    }


    var isFirst = true

    var start = -90f
    var end = -270f
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isFirst) {
            rectF.offset(strokeWidth, strokeWidth)
            val color1 = ContextCompat.getColor(context, R.color.color_faee65)
            val color2 = ContextCompat.getColor(context, R.color.color_eeaf47)
            val shader = SweepGradient(rectF.centerX(), rectF.centerY(), color2, color1)
            val matrix = Matrix()
            matrix.postRotate(-90f, rectF.centerX(), rectF.centerY())
            shader.setLocalMatrix(matrix)
            gradientPaint.shader = shader
            isFirst = false
        }

        canvas.drawCircle(circleX, circleY, circleRadius, bgPaint)

        for (index in 1..36) {
            canvas.drawArc(rectF, (index * 10f) + 2f, 8f, true, grayPaint)
        }

        for (index in 0..currentIndex) {
            canvas.drawArc(rectF, -((index * 10f) + 2f) - 90f, -8f, true, gradientPaint)
        }

        canvas.drawCircle(circleX, circleY, innerCircleRadius - strokeWidth, bgPaint)

    }


    companion object {
        private const val TAG = "FloatingView"
    }
}