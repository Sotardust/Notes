package com.dht.notes.testcode.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.dht.notes.R
import com.dht.notes.testcode.other.ontouch.util.ScreenUtil

/**
 *  created by Administrator on 2020/9/8 17:53
 */

class FlowView : View {

    private val TAG = "dht1"

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private lateinit var paint: Paint

    //文本间隔宽度
    private val intervalWidth = 30f
    //文本间隔高度
    private val intervalHeight = 10f
    private lateinit var bitmap: Bitmap

    private fun init(context: Context) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true
        paint.strokeWidth = 1f
        paint.style = Paint.Style.FILL
        paint.textSize = 50f
        mHeight = paint.descent() - paint.ascent()
//        val ist  = resources.openRawResource(R.drawable.bg_text)
        bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.text_bound)
    }


    private val mData: MutableList<String> = mutableListOf()

    //开始绘制
    fun setInstanceData(data: List<String>) {
        mData.clear()
        mData.addAll(data)
//        invalidate()
    }

    private var initX = 0f
    private var initY = 0f
    private var level = 1
    private var mHeight = 0f
    private var length = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        length = mData.size - 1
        if (length == 0) return
        for (index in 0..length) {
            val width = paint.measureText(mData[index])
            initX += intervalWidth
            if (index < length && initX + paint.measureText(mData[index + 1]) > ScreenUtil.WIDTH) {
                initX = intervalWidth
                level++
            }
            initY = (mHeight + intervalHeight) * level
            Log.d(TAG, ": initX  $initX ,initY =  $initY  width =${width} mHeight =${mHeight}")
            canvas.drawText(mData[index], initX, initY, paint)
            initX += width
        }
        bitmap.recycle()
    }

}