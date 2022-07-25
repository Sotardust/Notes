package com.dht.notes.code.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import com.dht.notes.R


/**
 * wifi防蹭网 波纹View
 */
class WifiWaveView : View {

    private var centerRadius = dip2px(context, 4f)
    private var maxRadius = dip2px(context, 14f)
    private var waveIntervalTime = 500
    private var waveDuration = 1500
    private var running: Boolean = false
    private var waveList = mutableListOf<Wave>()
    private val wavePaint = Paint()


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)


    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attributeSet,
            defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WifiWaveView, defStyleAttr, 0)
        centerRadius = typedArray.getDimension(R.styleable.WifiWaveView_center_radius, centerRadius.toFloat()).toInt()
        maxRadius = typedArray.getDimension(R.styleable.WifiWaveView_max_radius, maxRadius.toFloat()).toInt()
        waveIntervalTime = typedArray.getInt(R.styleable.WifiWaveView_wave_interval_time, waveIntervalTime)
        waveDuration = typedArray.getInt(R.styleable.WifiWaveView_wave_duration, waveDuration)

        wavePaint.strokeWidth = typedArray.getDimension(R.styleable.WifiWaveView_wave_width, 1.0f)
        wavePaint.style = Paint.Style.STROKE
        wavePaint.color = typedArray.getColor(
                R.styleable.WifiWaveView_wave_side_color,
                ContextCompat.getColor(context, R.color.colorAccent)
        )
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        val radius = (w.coerceAtMost(h) / 2.0f).toInt()
        if (radius < maxRadius) {
            maxRadius = radius
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawWave(canvas)
    }


    /**
     * 绘制波纹
     */
    private fun drawWave(canvas: Canvas) {
        waveList.forEach {
            wavePaint.alpha = it.getAlpha()
            canvas.drawCircle(width / 2f, height / 2f, it.getCurrentRadius(), wavePaint)
        }
    }

    fun start() {
        running = true
        waveList.add(Wave())

    }

    fun stop() {
        running = false
        waveList.forEach { it.cancelAnimation() }
    }

    private inner class Wave {

        private var hasCreateNewWave = false

        private val createWaveAnimation = ValueAnimator.ofFloat(0f, 1.0f).apply {
            this.interpolator = LinearInterpolator()
            this.duration = waveDuration.toLong()
            this.start()
            this.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (running) {
                        waveList.remove(this@Wave)
                    }
                }
            })
            addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                override fun onAnimationUpdate(animation: ValueAnimator) {

                    percent = animation.animatedValue as Float
                    Log.d(Companion.TAG, "onAnimationUpdate: ${animation.animatedValue}")
                    if (running && percent >= waveIntervalTime.toFloat() / waveDuration.toFloat() && !hasCreateNewWave) {
                        waveList.add(Wave())
                        hasCreateNewWave = true
                    }
                    invalidate()

                }
            })
        }

        var percent: Float = 0f
        // @Keep
        // set(value) {
        //     field = value
        //
        //     Log.d("dht", "null() called $value")
        //     if (running && value >= waveIntervalTime.toFloat() / waveDuration.toFloat() && !hasCreateNewWave) {
        //         waveList.add(Wave())
        //         hasCreateNewWave = true
        //     }
        //     invalidate()
        // }

        fun cancelAnimation() {
            createWaveAnimation.cancel()
        }

        fun getAlpha(): Int {
            return (255 * (1 - percent)).toInt()
        }

        fun getCurrentRadius(): Float {
            return centerRadius + percent * (maxRadius - centerRadius)
        }
    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    companion object {
        private const val TAG = "dht"
    }
}