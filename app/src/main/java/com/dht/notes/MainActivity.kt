package com.dht.notes

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.dht.notes.testcode.ontouch.TestActivity
import com.dht.notes.testcode.ontouch.TestToastActivity
import com.dht.notes.testcode.ontouch.bitmap.BitmapActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 学习笔记
 */
class MainActivity : AppCompatActivity() {

    val TAG:String ="MainActivity" ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         btn.setOnClickListener {
             val intent = Intent(MainActivity@this, TestToastActivity::class.java)
//             intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
             startActivity(intent)
         }
//        println("android.osProcess.myPid()   = " + android.os.Process.myPid())
//        Log.d(TAG, "onCreate() returned: " + Thread.currentThread().id)
//        Log.d(TAG, "Looper.getMainLooper().getThread() " + Looper.getMainLooper().thread.id)
        val thread = Thread(Runnable { Log.d(TAG, "run: ") })
        thread.start()

        Log.d(TAG, "onCreate() returned: getTaskId()" + taskId)
        Log.d(TAG, " thread.getId() returned: " + thread.id)

    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart " )
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart " )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume " )
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause " )
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop " )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy " )
    }
}
