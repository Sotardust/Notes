package com.dht.notes.code.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * @author dht
 * @date 2023/3/16 16:26
 **/
class CircleProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint()

    private val paint = Paint()

    private val rect = RectF()

    private var sweepAngle = -90f

    init {

        bgPaint.style = Paint.Style.STROKE
        bgPaint.strokeWidth = 10f
        bgPaint.color = ContextCompat.getColor(context, R.color.color_EEEEEE)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = ContextCompat.getColor(context, R.color.color_0055FF)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rect.set(width / 4f, height / 4f, width * 3 / 4f, height * 3 / 4f)
        canvas.drawArc(rect, 0f, 360f, false, bgPaint)
        canvas.drawArc(rect, -90f, 270f, false, paint)

    }

    fun startAnim(onProgress: (progress: String) -> Unit, onEnd: () -> Unit) {
        val animator = ValueAnimator.ofInt(-90, 270)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            sweepAngle = value.toFloat()
            val progress = ((value + 90) / 3.6).toInt()
            onProgress.invoke("$progress%")
        }
//        animator.addListener( {
//            onProgress.invoke("100%")
//            onEnd.invoke()
//        })
        animator.start()

    }
}