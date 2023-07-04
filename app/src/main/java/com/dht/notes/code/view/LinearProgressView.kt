package com.dht.notes.code.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * @author dht
 * @date 2023/3/16 16:26
 **/
class LinearProgressView @JvmOverloads constructor(context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint()

    private val paint = Paint()

    private val bgRect = RectF()
    private val rect = RectF()

    private var sweepAngle = -90f

    private val animator = ValueAnimator.ofInt(0, 360)

    init {

        bgPaint.style = Paint.Style.FILL
        bgPaint.color = ContextCompat.getColor(context, R.color.color_EEEEEE)

        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, R.color.color_0055FF)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bgRect.set(0f, 0f, width.toFloat(), height.toFloat())
        rect.set(0f, 0f, width.toFloat() / 2f, height.toFloat())
        canvas.drawRoundRect(bgRect, 10f, 10f, bgPaint)
        canvas.drawRoundRect(rect, 10f, 10f, paint)
    }

}