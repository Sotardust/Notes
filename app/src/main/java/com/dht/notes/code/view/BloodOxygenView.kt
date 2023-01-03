package com.dht.notes.code.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * created by dht on 2023/1/3 10:28
 */
class BloodOxygenView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val GRADIENT_COLORS = intArrayOf(Color.parseColor("#0074ef"), Color.parseColor("#1a7afd"))

    private var sweepAngle = 0f

    private var defaultSweepAngle = 300f

    private var startSweepAngle = 120f

    private var whitePaint: Paint = Paint()

    private var paint: Paint = Paint()

    private val width = 440f

    private val height = 440f

    private var rectF: RectF = RectF()

    private var isFirstDraw = true

    init {
        whitePaint.isAntiAlias = true
        whitePaint.color =  ContextCompat.getColor(context,R.color.white)
        whitePaint.style = Paint.Style.STROKE
        whitePaint.strokeWidth = 30f
        whitePaint.strokeCap = Paint.Cap.ROUND

        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30f
        paint.strokeCap = Paint.Cap.ROUND
        @SuppressLint("DrawAllocation")
        val shader = LinearGradient(0f, 0f, width, height, GRADIENT_COLORS, null, Shader.TileMode.MIRROR)
        paint.shader = shader

        rectF.set(20f, 20f, width, height)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(rectF, startSweepAngle, defaultSweepAngle, false, whitePaint)

        canvas.drawArc(rectF, startSweepAngle, sweepAngle, false, paint)

    }

    fun setProgress(factor: Float) {

        val animator = ValueAnimator.ofFloat(0f, factor)
        animator.interpolator = LinearInterpolator()
        animator.duration = 2500
        animator.addUpdateListener { p0 ->
            sweepAngle = (p0.animatedValue as Float) * defaultSweepAngle
            postInvalidate()
        }
        animator.start()
    }
}