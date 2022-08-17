package com.dht.notes.code.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


public class ShakeSensorListener implements SensorEventListener {

    private static final String TAG = "HookSetOnClickListenerH";

    static SensorEvent sensorEvent;

    @Override
    public void onSensorChanged(SensorEvent event) {
        // sensorEvent = event;
        //避免一直摇
        // if (isShake) {
        //     return;
        // }
        // // 开始动画
        // anim.start();
        float[] values = event.values;
        /*
         * x : x轴方向的重力加速度，向右为正
         * y : y轴方向的重力加速度，向前为正
         * z : z轴方向的重力加速度，向上为正
         */
        float x = Math.abs(values[0]);
        float y = Math.abs(values[1]);
        float z = Math.abs(values[2]);

        // Log.d(TAG, " onSensorChanged() called with: x = [" + x + "]  y = [" + y + "]  z = [" + z + "]");
        //加速度超过19，摇一摇成功
        // if (x > 19 || y > 19 || z > 19) {
        //     isShake = true;
        //     //播放声音
        //     playSound(MainActivity.this);
        //     //震动，注意权限
        //     vibrate( 500);
        //     //仿网络延迟操作，这里可以去请求服务器...
        //     new Handler().postDelayed(new Runnable() {
        //         @Override
        //         public void run() {
        //             //弹框
        //             showDialog();
        //             //动画取消
        //             anim.cancel();
        //         }
        //     },1000);
        // }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged() called with: sensor = [" + sensor + "], accuracy = [" + accuracy + "]");
    }

}