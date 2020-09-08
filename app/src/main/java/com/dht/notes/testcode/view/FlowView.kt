package com.dht.notes.testcode.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 *  created by Administrator on 2020/9/8 17:53
 */

class FlowView : View {

    private val TAG = "dht"

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private var paint: Paint? = null

    private fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint?.isAntiAlias = true
        paint?.strokeWidth = 1f
        paint?.style = Paint.Style.FILL
        paint?.textSize = 50f
    }

    private val mData: MutableList<String> = mutableListOf()

    private var currentValue: String? = null
    //开始绘制
    private fun setInstanceData(data: List<String>) {
        mData.clear()
        mData.addAll(data)
        for (value in mData) {
            canvasStart(value)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(TAG, ": canvas " + canvas.height + " , " + canvas.width)

        if (mData.size == 0) return
        canvas.drawText(currentValue!!, 100f, 800f, paint)


    }

    private fun canvasStart(value: String) {
        currentValue = value
        invalidate()
    }

}