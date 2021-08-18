package com.dht.notes.code.floatingwindow

import android.view.View

/**
 * created by dht on 2021/8/18 16:56
 */

fun View.dp2px(dp: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}