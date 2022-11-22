package com.dht.notes.code.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.dht.notes.R

/**
 * created by dht on 2022/10/27 09:47
 */
class ProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val rectF = RectF()

    var progressWidth = 0f
    var progressHeight = 100f
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bgColor = ContextCompat.getColor(context, R.color.color_6F6F70)
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_charge_view)

    init {
        paint.color = bgColor
        paint.isAntiAlias = true
        paint.strokeWidth = 5f

        viewTreeObserver.addOnPreDrawListener {
            Log.d("ProgressView", "null() called width =$width height =$height")
            true
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // val src = RectF(0f, progressHeight / 2, 0f, 0f)
        //
        //
        // canvas.drawBitmap(bitmap, null, drs, null)
        // rectF.set(0f, 0f, progressWidth, progressWidth)
        //
        //
        // canvas.drawRoundRect(rectF, progressWidth / 2, progressHeight / 2, paint)
        //

        // bitmap.recycle()
    }

    fun updateViewWidth(width: Float) {
        progressWidth = width

        layoutParams = LinearLayoutCompat.LayoutParams(width.toInt(),100)
        // invalidate()

        // setBackgroundResource(R.drawable.ic_charge_progress)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    // override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    //
    //     super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    //
    //     val width = getDefaultSize(progressWidth.toInt(), widthMeasureSpec)
    //     val height = getDefaultSize(progressHeight.toInt(), heightMeasureSpec)
    //
    //     Log.d("ProgressView", "onMeasure() called with: width = $width, height = $height")
    //     setMeasuredDimension(progressWidth.toInt(), height)
    // }
    // override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    //     Log.d("ProgressView", "onSizeChanged() called with: w = $w, h = $h, oldw = $oldw, oldh = $oldh")
    //
    //     super.onSizeChanged(progressWidth.toInt(), h, oldw, oldh)
    //
    //     Log.d("ProgressView", "onSizeChanged() called with: progressWidth = $progressWidth, h = $h, oldw = $oldw, oldh = $oldh")
    // }


}


