package com.dht.notes

import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * 学习笔记
 */
class MainActivity : AppCompatActivity() {

    val TAG:String ="MainActivity" ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("android.os.Process.myPid()   = " + android.os.Process.myPid())
        Log.d(TAG, "onCreate() returned: " + Thread.currentThread().id)
        Log.d(TAG, "Looper.getMainLooper().getThread() " + Looper.getMainLooper().thread.id)
        val thread = Thread(Runnable { Log.d(TAG, "run: ") })
        thread.start()

        Log.d(TAG, " thread.getId() returned: " + thread.id)

    }
}
