package com.dht.notes.code.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


class ShakeHookProxy implements SensorEventListener {

    static final String TAG = "ShakeSensorListener";
    private ShakeSensorListener shakeSensorListener;

    public ShakeHookProxy(SensorEventListener sensorEventListener) {
        this.shakeSensorListener = (ShakeSensorListener) sensorEventListener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] floats = new float[event.values.length];
        for (int i = 0; i < floats.length; i++) {
            floats[i] = event.values[i] + 20;
        }
        System.arraycopy(floats, 0, event.values, 0, event.values.length);
        shakeSensorListener.onSensorChanged(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    // @Override
    // public void onSensorChanged(SensorEvent event) {
    //
    // }
    //
    // @Override
    // public void onAccuracyChanged(Sensor sensor, int accuracy) {
    //
    // }
}