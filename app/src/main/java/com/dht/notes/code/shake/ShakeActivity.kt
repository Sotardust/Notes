package com.dht.notes.code.shake

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import com.dht.notes.NoteApplication
import com.dht.notes.R

/**
 * created by dht on 2022/8/10 15:27
 */
class ShakeActivity : Activity() {

    private var shakeListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            Log.d("TAG", "hook() onSensorChanged() called with: event = $event")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        sensorManager = NoteApplication.getApplication().getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        sensorManager.registerListener(ShakeSensorListener(), sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        super.onResume()

        HookSensorManager.reflectSensorListenerList()
    }

    override fun onPause() {
        //取消注册
        sensorManager.unregisterListener(shakeListener)
        super.onPause()
    }

}