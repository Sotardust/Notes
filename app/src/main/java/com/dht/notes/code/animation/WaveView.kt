// package com.dht.notes.code.animation
//
//
// import android.animation.Animator
// import android.animation.AnimatorListenerAdapter
// import android.animation.ObjectAnimator
// import android.annotation.SuppressLint
// import android.content.Context
// import android.graphics.Canvas
// import android.graphics.Color
// import android.graphics.Paint
// import android.graphics.SweepGradient
//
// import android.util.AttributeSet
// import android.util.Log
// import android.view.View
// import android.view.animation.Animation
// import android.view.animation.LinearInterpolator
// import androidx.annotation.Keep
// import androidx.core.content.ContextCompat
// import com.dht.notes.R
//
//
// /**
//  * 水波纹扩散view
//  */
// class WaveView : View {
//
//     private var centerColor: Int = ContextCompat.getColor(context, R.color.color_897EFF)
//     private var centerRadius = Utils.dip2px(context, 4f)
//     private var maxRadius = Utils.dip2px(context, 14f)
//     private var waveIntervalTime = 500
//     private var waveDuration = 1500
//     private var running: Boolean = false
//     private var waveList = mutableListOf<Wave>()
//     private var waveWidth = Utils.dip2px(context, 1.0f)
//     private val paint = Paint()
//     private val rotatePaint = Paint()
//
//
//     constructor(context: Context) : this(context, null)
//
//     constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
//
//     constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
//             context,
//             attributeSet,
//             defStyleAttr
//     ) {
//         val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WaveView, defStyleAttr, 0)
//         centerColor = typedArray.getColor(
//                 R.styleable.WaveView_center_color,
//                 ContextCompat.getColor(context, R.color.color_897EFF)
//         )
//         centerRadius = typedArray.getDimension(R.styleable.WaveView_center_radius, 4f).toInt()
//         maxRadius = typedArray.getDimension(R.styleable.WaveView_max_radius, 14f).toInt()
//         waveWidth = typedArray.getDimension(R.styleable.WaveView_wave_width, 1.0f).toInt()
//         waveIntervalTime = typedArray.getInt(R.styleable.WaveView_wave_interval_time, 500)
//         waveDuration = typedArray.getInt(R.styleable.WaveView_wave_duration, 1500)
//         paint.color = centerColor
//
//
//         paint.strokeWidth = waveWidth.toFloat()
//         paint.style = Paint.Style.STROKE
//
//         val colors = arrayListOf(context.resources.getColor(R.color.color_00ffffff), context.resources.getColor(R.color.color_00ffffff)
//         )
//         shader = SweepGradient(300f, 300f,
//                 context.resources.getColor(R.color.color_00ffffff),
//                 context.resources.getColor(R.color.color_ffffff))
//
//         rotatePaint.color = centerColor
//         rotatePaint.strokeWidth = waveWidth.toFloat() * 5
//         rotatePaint.style = Paint.Style.STROKE
//         typedArray.recycle()
//
//
//     }
//
//     var shader: SweepGradient? = null
//
//     override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//
//         Log.d(Companion.TAG, "onSizeChanged() called with: w = $w, h = $h, oldw = $oldw, oldh = $oldh")
//         val radius = (w.coerceAtMost(h) / 2.0f).toInt()
//         if (radius < maxRadius) {
//             maxRadius = radius
//         }
//     }
//
//
//     var rotateView: RotateView? = null
//     override fun onDraw(canvas: Canvas) {
//         super.onDraw(canvas)
// //        drawWave(canvas)
//
//         rotatePaint.shader = shader
//         canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), centerRadius.toFloat(), rotatePaint)
//         Log.d(TAG, "onDraw() called with: canvas = $canvas")
//     }
//
//
//     /**
//      * 绘制波纹
//      */
//     private fun drawWave(canvas: Canvas) {
//         waveList.forEach {
//             paint.alpha = it.getAlpha()
//             canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), it.getCurrentRadius(), paint)
//         }
// //        if (waveList.size > 0) {
// //            paint.alpha = 255
// //            paint.style = Paint.Style.FILL
// //            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), centerRadius.toFloat(), paint)
// //        }
//     }
//
//     fun startWave() {
//         running = true
//         waveList.add(Wave())
//     }
//
//     fun stopWave() {
//         running = false
//         waveList.forEach { it.cancelAnimation() }
//     }
//
//
//     inner class RotateView {
//
//     }
//
//     private inner class Wave {
//
//         private var hasCreateNewWave = false
//
//         private val createWaveAnimation = ObjectAnimator.ofFloat(this, "percent", 0f, 1.0f).apply {
//             this.interpolator = LinearInterpolator()
//             this.duration = waveDuration.toLong()
//             this.start()
//             this.addListener(object : AnimatorListenerAdapter() {
//                 override fun onAnimationEnd(animation: Animator?) {
//                     if (running) {
//                         waveList.remove(this@Wave)
//                     }
//                 }
//             })
//         }
//
//         var percent: Float = 0f
//             @Keep
//             set(value) {
//                 field = value
//                 if (running && value >= waveIntervalTime.toFloat() / waveDuration.toFloat() && !hasCreateNewWave) {
//                     waveList.add(Wave())
//                     hasCreateNewWave = true
//                 }
//                 invalidate()
//             }
//
//         fun cancelAnimation() {
//             createWaveAnimation.cancel()
//         }
//
//         fun getAlpha(): Int {
//             return (255 * (1 - percent)).toInt()
//         }
//
//         fun getCurrentRadius(): Float {
//             return centerRadius + percent * (maxRadius - centerRadius)
//         }
//     }
//
//     companion object {
//         private const val TAG = "dht"
//     }
// }