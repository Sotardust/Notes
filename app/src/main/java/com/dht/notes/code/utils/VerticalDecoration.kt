package com.dht.notes.code.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dht.notes.R


/**
 * LinearLayoutManager 分割线
 *
 *
 * created by dht on 2019/1/22 10:30
 */
class VerticalDecoration : RecyclerView.ItemDecoration {

    private var height: Int = 0
    private var width: Int = 0
    private var paint: Paint? = null
    private var colorRes = R.color.line_E8E8E8

    constructor(height: Int) {
        initData(0, height, colorRes)

    }

    constructor(width: Int, height: Int) {
        initData(width, height, colorRes)
    }

    constructor(width: Int, height: Int, colorRes: Int) {
        initData(width, height, colorRes)
    }

    /**
     * 初始化数据
     *
     * @param width    宽度
     * @param height   高度
     * @param colorRes 分割线颜色
     */
    private fun initData(width: Int, height: Int, colorRes: Int) {
        this.width = width
        this.height = height
        this.colorRes = colorRes
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.style = Paint.Style.FILL


    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State) {

        super.getItemOffsets(outRect, view, parent, state)
        //        if (parent.getChildLayoutPosition(view) != 0) {//第一行不设置距离top高度
        outRect.top = if (parent.getChildLayoutPosition(view) != 0) height else 2
        //        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        var left = parent.paddingLeft
        var right = parent.measuredWidth - parent.paddingRight
        if (width != 0) {
            left += width
            right -= width
        }
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val bottom = child.top
            val top = child.top - height
            paint!!.color = parent.context.resources.getColor(colorRes)
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint!!)
        }
    }

    companion object {
        private val TAG = "VerticalDecoration"
    }
}
