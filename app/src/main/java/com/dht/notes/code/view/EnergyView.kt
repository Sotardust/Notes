package com.dht.notes.code.view

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
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * created by dht on 2023/1/3 10:28
 */
class EnergyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val markColor = Color.parseColor("#4393fb")

    private val bgColor = Color.parseColor("#e8edf1")

    private var paint2: Paint = Paint()

    private var paint: Paint = Paint()

    private var paint1: Paint = Paint()

    private val width = 340f
    private val height = 120f

    private var rectF: RectF = RectF()
    private var rectF1: RectF = RectF()
    private var rectF2: RectF = RectF()

    private var markWidth = 2f

    private var spaceCount = 15

    private var defaultWidth = width / spaceCount

    init {

        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = bgColor

        rectF.set(0f, 0f, width, height)

        paint1.isAntiAlias = true
        paint1.style = Paint.Style.FILL

        paint1.color = markColor

        rectF1.set(0f, 0f, 0f, height)

        paint2.isAntiAlias = true
        paint2.style = Paint.Style.FILL
        paint2.color = ContextCompat.getColor(context, R.color.white)


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(rectF, paint)
        canvas.drawRect(rectF1, paint1)

        for (index in 0 until spaceCount) {

            rectF2.set(index * defaultWidth, 0f, index * defaultWidth - markWidth, height)

            canvas.drawRect(rectF2, paint2)
        }
    }

    fun setProgress(factor: Float) {

        rectF1.set(0f, 0f, factor * width, height)

        postInvalidate()

    }
}