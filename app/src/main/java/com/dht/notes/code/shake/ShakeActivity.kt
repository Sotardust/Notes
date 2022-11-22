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
import java.util.*

/**
 * created by dht on 2022/8/10 15:27
 */
class ShakeActivity : Activity() {

    private val TAG = "ShakeActivity.TAG"
    private var shakeListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {

            Log.d(TAG, "event = ${event?.values?.toList()}")
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d(TAG, " sensor = $sensor, accuracy = $accuracy")
        }

    }

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        sensorManager = NoteApplication.getApplication().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), 200000 * 100);

    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(shakeListener)
    }
}