package com.dht.notes.code.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_wifi_wave.*

class WifiWaveActivity : Activity() {

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wifi_wave)
//        waveView.startWave()

        val radarView = findViewById<View>(R.id.radar) as RadarView

        ObjectAnimator.ofFloat(radarView,
                "rotation", 0f, 360f)
                .apply {

                    interpolator = LinearInterpolator()
                    duration = 1500L
                    this.repeatCount = ObjectAnimator.INFINITE
                    this.repeatMode = ObjectAnimator.RESTART
                    start()
                    addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                        override fun onAnimationUpdate(animation: ValueAnimator?) {
                            Log.d(Companion.TAG, "onAnimationUpdate() called with: animation = $animation")
//                            waveView.update()
                        }

                    })
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)


                        }

                    })
                }
    }

    override fun onDestroy() {
        super.onDestroy()
//        waveView.stopWave()
    }

    companion object {
        private const val TAG = "dht"
    }
}