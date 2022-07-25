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
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * created by dht on 2021/8/18 15:33
 */
class FloatingView : View {


    /**
     * 内圈小圆的圆心
     */
    private var circleX = 0f
    private var circleY = 0f

    /**
     * 外圈大圆的半径
     */
    private var circleRadius = 220f

    /**
     * 内圈小圆的半径
     */
    private var innerCircleRadius = 0f

    /**
     * 圆环宽度
     */
    private var circleWidth = 15f

    /**
     * 动画持续时间
     */
    private var duration = 1000

    /**
     * 扇形所在的矩形区域
     */
    private val rectF = RectF()

    //渐变开始、结束颜色、
    private var gradientStartColor = ContextCompat.getColor(context, R.color.color_faee65)
    private var gradientEndColor = ContextCompat.getColor(context, R.color.color_eeaf47)

    /**
     * 圆背景颜色
     */
    private var bgColor = ContextCompat.getColor(context, R.color.color_6F6F70)

    /**
     * 圆环背景颜色
     */
    private var grayColor = ContextCompat.getColor(context, R.color.color_8A8A8A)

    /**
     * 设置不同画笔
     */
    private val bgPaint = getPaint()
    private val gradientPaint = getPaint()
    private val grayPaint = getPaint()

    /**
     * 动画执行的当前索引
     */
    private var currentIndex = 0

    private var valueAnimator: ValueAnimator? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatingView, defStyleAttr, 0)
        bgColor = typedArray.getColor(R.styleable.FloatingView_bg_paint_color, bgColor)
        gradientStartColor = typedArray.getColor(R.styleable.FloatingView_gradient_start_paint_color, gradientStartColor)
        gradientEndColor = typedArray.getColor(R.styleable.FloatingView_gradient_end_paint_color, gradientEndColor)
        grayColor = typedArray.getColor(R.styleable.FloatingView_gray_paint_color, grayColor)
        duration = typedArray.getInt(R.styleable.FloatingView_duration, duration)
        circleWidth = typedArray.getDimension(R.styleable.FloatingView_ring_width, circleWidth)
        circleRadius = typedArray.getDimension(R.styleable.FloatingView_circle_radius, circleRadius)
        typedArray.recycle()
        initData()
    }

    /**
     * 设置画笔
     */
    private fun getPaint(): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 5f
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {

        //外圈扇形所在矩形区域
        rectF.set(0f, 0f, 2 * (circleRadius - circleWidth), 2 * (circleRadius - circleWidth))
        circleX = circleRadius
        circleY = circleRadius
        innerCircleRadius = circleRadius - 2 * circleWidth
        bgPaint.color = bgColor
        grayPaint.color = grayColor

        //设置矩形圆点偏移量
        rectF.offset(circleWidth, circleWidth)

        val shader = SweepGradient(rectF.centerX(), rectF.centerY(), gradientEndColor, gradientStartColor)
        val matrix = Matrix()
        matrix.postRotate(-90f, rectF.centerX(), rectF.centerY())
        shader.setLocalMatrix(matrix)
        gradientPaint.shader = shader
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(circleX, circleY, circleRadius, bgPaint)

        drawCircle(canvas)

        canvas.drawCircle(circleX, circleY, innerCircleRadius, bgPaint)

    }


    /**
     * 绘制圆环
     */
    private fun drawCircle(canvas: Canvas) {

        for (index in 1..36) {
            canvas.drawArc(rectF, (index * 10f) + 2f, 8f, true, grayPaint)
        }

        for (index in 0..currentIndex) {
            canvas.drawArc(rectF, -((index * 10f) + 2f) - 90f, -8f, true, gradientPaint)
        }
    }

    /**
     * 开始动画
     * @param degree 内存使用程度
     */
    fun startAnima(degree: Int) {
        valueAnimator = ValueAnimator.ofInt(0, degree * 35 / 100).apply {
            this.interpolator = LinearInterpolator()
            this.duration = duration
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

    /**
     * 停止动画
     */
    fun stopAnima() {
        valueAnimator?.cancel()
    }


    companion object {
        private const val TAG = "FloatingView"
    }
}