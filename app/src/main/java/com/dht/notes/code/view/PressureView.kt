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
class PressureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val GB_GRADIENT_COLORS = intArrayOf(Color.parseColor("#ffeac6"), Color.parseColor("#ffe35d"))

    private val MARK_GRADIENT_COLORS = intArrayOf(Color.parseColor("#ffe1c1"), Color.parseColor("#ffc658"))

    private var paint2: Paint = Paint()

    private var paint: Paint = Paint()

    private var paint1: Paint = Paint()

    private val width = 340f
    private val height = 120f

    private var rectF: RectF = RectF()
    private var rectF1: RectF = RectF()
    private var rectF2: RectF = RectF()

    private var markWidth = 5f

    init {

        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        @SuppressLint("DrawAllocation")
        val bgShader = LinearGradient(0f, 0f, width, height, GB_GRADIENT_COLORS, null, Shader.TileMode.MIRROR)
        paint.shader = bgShader
        rectF.set(0f, 0f, width, height)

        paint1.isAntiAlias = true
        paint1.style = Paint.Style.FILL
        @SuppressLint("DrawAllocation")
        val shader = LinearGradient(0f, 0f, width / 2, height, MARK_GRADIENT_COLORS, null, Shader.TileMode.MIRROR)
        paint1.shader = shader
        rectF1.set(0f, 0f, width / 3 - markWidth, height)

        paint2.isAntiAlias = true
        paint2.style = Paint.Style.FILL
        paint2.color = ContextCompat.getColor(context, R.color.white)

        rectF2.set(width / 3 - markWidth, 0f, width / 3, height)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(rectF, paint)
        canvas.drawRect(rectF1, paint1)
        canvas.drawRect(rectF2, paint2)
    }

    fun setProgress(factor: Float) {

        rectF1.set(0f, 0f, factor * width - markWidth, height)

        rectF2.set(factor * width - markWidth, 0f, factor * width, height)

        postInvalidate()

    }
}