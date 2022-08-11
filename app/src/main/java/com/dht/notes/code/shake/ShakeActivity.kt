package com.dht.notes.code.shake

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dht.notes.R
import java.lang.reflect.Method

/**
 * created by dht on 2022/8/10 15:27
 */
class ShakeActivity : Activity() {

    private var shakeListener = object: SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).vendor

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            HookSetOnClickListenerHelper.hook(this, sensorManager)
        },1000)

        super.onResume()

    }

    override fun onPause() {
        //取消注册
        sensorManager.unregisterListener(shakeListener)
        super.onPause()
    }

}