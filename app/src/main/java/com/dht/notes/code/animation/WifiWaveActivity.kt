package com.dht.notes.code.animation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_wifi_wave.*

class WifiWaveActivity : Activity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wifi_wave)

        btn.setOnClickListener {
            wifi_wave.stop()
        }
        startbtn.setOnClickListener {
            wifi_wave.start()
        }
        wifi_wave.start()
//        waveView.startWave()

//         val radarView = findViewById<View>(R.id.radar) as RadarView
//
//         ObjectAnimator.ofFloat(radarView,
//                 "rotation", 0f, 360f)
//                 .apply {
//
//                     interpolator = LinearInterpolator()
//                     duration = 1500L
//                     this.repeatCount = ObjectAnimator.INFINITE
//                     this.repeatMode = ObjectAnimator.RESTART
//                     start()
//                     addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
//                         override fun onAnimationUpdate(animation: ValueAnimator?) {
//                             Log.d(Companion.TAG, "onAnimationUpdate() called with: animation = $animation")
// //                            waveView.update()
//                         }
//
//                     })
//                     addListener(object : AnimatorListenerAdapter() {
//                         override fun onAnimationEnd(animation: Animator?) {
//                             super.onAnimationEnd(animation)
//
//
//                         }
//
//                     })
//                 }
    }

    override fun onDestroy() {
        super.onDestroy()
        wifi_wave.stop()
    }

    companion object {
        private const val TAG = "dht"
    }
}