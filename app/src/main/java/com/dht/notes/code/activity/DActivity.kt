package com.dht.notes.code.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_a.*


/**
 *  created by Administrator on 2020/9/8 17:20
 */

class DActivity : Activity() {

    private val TAG = "dht DActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)
        Log.d(TAG, "onCreate: ")
        Log.d(TAG, "onCreate: 添加日志信息")
        btnText.text = "D跳转到B"
        btnText.setOnClickListener {
            startActivity(Intent(this, BActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
        Log.d(TAG, "用于测试git 命令: ")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $taskId")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged: ")
    }
}