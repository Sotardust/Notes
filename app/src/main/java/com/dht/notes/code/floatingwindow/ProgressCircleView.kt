package com.dht.notes.code.floatingwindow

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * created by dht on 2021/8/18 15:33
 */
class ProgressCircleView : View {


    /**
     * 内圈小圆的圆心
     */
    private var circleX = 0f
    private var circleY = 0f

    /**
     * 外圈大圆的半径
     */
    private var circleRadius = 130f

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
    private var arcDuration = 1000L

    /**
     * 扇形所在的矩形区域
     */
    private val rectF = RectF()


    private var startColor = ContextCompat.getColor(context, R.color.color_2f73f3)

    /**
     * 圆背景颜色
     */
    private var bgColor = ContextCompat.getColor(context, R.color.white)

    /**
     * 圆环背景颜色
     */
    private var grayColor = ContextCompat.getColor(context, R.color.color_bdbdbd)

    /**
     * 圆弧旋转角度
     */
    private var sweepAngle = 0f

    /**
     * 设置不同画笔
     */
    private val bgPaint = getPaint()
    private val circlePaint = getPaint()
    private val grayPaint = getPaint()

    private var valueAnimator: ValueAnimator? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        initData()
    }

    /**
     * 设置画笔
     */
    private fun getPaint(): Paint {
        return Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 10f
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //外圈扇形所在矩形区域
        rectF.set(0f, 0f, 2 * (circleRadius - circleWidth), 2 * (circleRadius - circleWidth))
        //设置矩形圆点偏移量
        rectF.offset(circleWidth, circleWidth)
        //外圈扇形所在矩形区域
        circleX = circleRadius
        circleY = circleRadius
        innerCircleRadius = circleRadius - circleWidth
        bgPaint.color = bgColor
        grayPaint.color = grayColor
        circlePaint.color = startColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(circleX, circleY, circleRadius, bgPaint)
        canvas.drawArc(rectF, -90f, -450f, true, grayPaint)
        canvas.drawArc(rectF, -90f, sweepAngle, true, circlePaint)
        canvas.drawCircle(circleX, circleY, innerCircleRadius - circleWidth * 2 / 3, bgPaint)

    }

    /**
     * 开始动画
     * @param degree 内存使用程度
     */
    fun startAnima(degree: Float) {
        valueAnimator = ValueAnimator.ofFloat(-0f, -(degree * 360 / 100)).apply {
            this.interpolator = LinearInterpolator()
            this.duration = arcDuration
            this.start()
            addUpdateListener { animation ->
                sweepAngle = animation.animatedValue as Float
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