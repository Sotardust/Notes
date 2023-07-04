package com.dht.notes.code.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * @author dht
 * @date 2023/5/25 10:33
 **/
class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint()

    private var animator: ValueAnimator? = null

    private val colors = intArrayOf(R.color.white,
        R.color.white_90,
        R.color.white_80,
        R.color.white_70,
        R.color.white_60,
        R.color.white_50,
        R.color.white_45,
        R.color.white_40,
        R.color.white_35,
        R.color.white_30,
        R.color.white_30)

    init {

        bgPaint.style = Paint.Style.FILL_AND_STROKE
        bgPaint.strokeWidth = 5f
        bgPaint.isAntiAlias = true
        bgPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawLoadingView(canvas)
    }

    private var tempIndex = 0

    @SuppressLint("ObjectAnimatorBinding")
    private fun drawLoadingView(canvas: Canvas) {

        for (index in 0..60) {
            if (index % 6 == 0) {
                if (tempIndex >= 11) {
                    tempIndex = 0
                }
                bgPaint.color = ContextCompat.getColor(context, colors[tempIndex++])
                canvas.drawLine(width / 2f, height / 2f + height / 2f / 7, width / 2f, height / 2f + height / 2f / 3,
                    bgPaint)

            }
            canvas.rotate(-6f, width / 2f, width / 2f)
        }

        if (animator == null) {
            animator = ValueAnimator.ofInt(0, 10).apply {
                duration = 1000
                interpolator = LinearInterpolator()
                repeatCount = ValueAnimator.INFINITE
                start()
                addUpdateListener {
                    val value = it.animatedValue as Int
                    if (tempIndex != value) {
                        invalidate()
                    }
                    tempIndex = value
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
        animator = null
    }
}