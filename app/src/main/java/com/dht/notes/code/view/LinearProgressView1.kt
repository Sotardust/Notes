package com.dht.notes.code.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * @author dht
 * @date 2023/3/16 16:26
 **/
class LinearProgressView1 @JvmOverloads constructor(context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint()
    private val tipBgPaint = Paint()

    private val paint = Paint()
    private val textPaint = Paint()

    private val bgRect = RectF()
    private val tipBgRect = RectF()

    private val rect = RectF()

    private var bitmap: Bitmap? = null

    private val bitmapWidth: Int = 65

    private val tipBgWidth = 70
    private val tipBgHeight = 50

    init {

        bgPaint.style = Paint.Style.FILL
        bgPaint.color = ContextCompat.getColor(context, R.color.color_EEEEEE)

        tipBgPaint.style = Paint.Style.FILL
        tipBgPaint.color = ContextCompat.getColor(context, R.color.color_131517)

        paint.style = Paint.Style.FILL

        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE
        textPaint.textSize = 25f
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_ball)
        drawable?.let { d ->
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapWidth,
                if (d.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
            bitmap?.let {
                val canvas = Canvas(it)
                d.setBounds(0, 0, it.width, it.height)
                d.draw(canvas)
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val top = height.toFloat() * 27 / 30f
        val bottom = height.toFloat() * 28 / 30f

        bgRect.set(0f, top, width, bottom)
        rect.set(0f, top, width / 2f, bottom)
        val linearGradient = LinearGradient(0f, top, 0f, bottom,
            ContextCompat.getColor(context, R.color.color_0055FF),
            ContextCompat.getColor(context, R.color.color_6F6F70), Shader.TileMode.REPEAT)
        paint.shader = linearGradient

        canvas.drawRoundRect(bgRect, 10f, 10f, bgPaint)
        canvas.drawRoundRect(rect, 10f, 10f, paint)

        bitmap?.let {
            canvas.drawBitmap(it, width / 2f - bitmapWidth / 2, top - bitmapWidth / 2 + 8, null)
            it.recycle()
        }
        bitmap = null

        val start = width / 2f
        val tipBgTop = top - bitmapWidth / 4 - tipBgHeight
        val tipBgStart = start - bitmapWidth / 4

        val tipBgBottom = top - bitmapWidth / 3

        tipBgRect.set(tipBgStart, tipBgTop, tipBgStart + tipBgWidth, tipBgBottom)

        canvas.drawRoundRect(tipBgRect, 10f, 10f, tipBgPaint)

        val path = Path()
        path.moveTo(tipBgStart + 15, tipBgBottom)
        path.lineTo(tipBgStart + 15, tipBgBottom + 10)
        path.lineTo(tipBgStart + 30, tipBgBottom)
        canvas.drawPath(path, tipBgPaint)
        var value = "0%"
        var length = if (value.length == 2) 20 else 13
        canvas.drawText(value, 0, value.length, tipBgStart + length, (tipBgTop + tipBgBottom) / 2 + 8, textPaint)
    }


}