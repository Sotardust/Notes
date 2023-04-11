package com.dht.notes.code.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author dht
 * @date 2023/3/16 16:26
 **/
class ProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint()

    private val leftPaint = Paint()
    private val rightPaint = Paint()

    private var strokeWidth = 12f
    var leftPosition = 0
    var rightPosition = 0

    init {

        bgPaint.style = Paint.Style.FILL
        bgPaint.strokeWidth = strokeWidth
        bgPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//        bgPaint.blendMode = BlendMode.SATURATION
        bgPaint.color = Color.parseColor("#66ffffff")

        leftPaint.style = Paint.Style.FILL
        leftPaint.strokeWidth = strokeWidth
        leftPaint.isAntiAlias = true
        leftPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        leftPaint.color = Color.parseColor("#25CB55")

        rightPaint.isAntiAlias = true
        rightPaint.style = Paint.Style.FILL
        rightPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        rightPaint.color = Color.parseColor("#E82127")
        rightPaint.strokeWidth = strokeWidth

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawLeftBg(canvas)

        drawRightBg(canvas)
    }

    private fun drawRightBg(canvas: Canvas) {
        for (index in 1..5) {

            val path = Path()

            path.moveTo(width - strokeWidth * index * 2.5f, -strokeWidth / 4)
            path.lineTo(
                width - strokeWidth * (index - 1) * 2.5f - strokeWidth / 2,
                height / 2f + strokeWidth / 4
            )
            canvas.drawPath(path, if (index <= 5 - leftPosition) bgPaint else leftPaint)
            val path1 = Path()
            path1.moveTo(
                width - strokeWidth * (index - 1) * 2.5f - strokeWidth / 2,
                height / 2f - strokeWidth / 4
            )
            path1.lineTo(width - strokeWidth * (index) * 2.5f, height * 1f + strokeWidth / 4)

            canvas.drawPath(path1, if (index <= 5 - leftPosition) bgPaint else leftPaint)
        }
    }

    private fun drawLeftBg(canvas: Canvas) {
        for (index in 1..5) {

            val path = Path()
            path.moveTo(strokeWidth * index * 2.5f, -strokeWidth / 4)
            path.lineTo(
                strokeWidth * (index - 1) * 2.5f + strokeWidth / 2,
                height / 2f + strokeWidth / 4
            )
            canvas.drawPath(path, if (index <= 5 - leftPosition) bgPaint else leftPaint)
            val path1 = Path()
            path1.moveTo(
                strokeWidth * (index - 1) * 2.5f + strokeWidth / 2,
                height / 2f - strokeWidth / 4
            )
            path1.lineTo(strokeWidth * (index) * 2.5f, height * 1f + strokeWidth / 4)

            canvas.drawPath(path1, if (index <= 5 - leftPosition) bgPaint else leftPaint)
        }
    }

    fun leftDraw(left: Int) {
        leftPosition = left
        invalidate()
    }

    fun rightDraw(left: Int) {
        rightPosition = left
        invalidate()
    }

}