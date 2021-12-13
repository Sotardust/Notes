package com.dht.notes.code.homekey

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_a.*
import java.util.*

/**
 *  created by Administrator on 2020/9/8 17:20
 */

class HomekeyBActivity : Activity() {

    private val TAG = "dht11B "
    private var lastClickTime = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        btnText.text = "跳转到HomeKeyBay"
        val SYSTEM_REASON = "reason"
        

        /**
         * Home键
         */
        val SYSTEM_HOME_KEY = "homekey"
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action ?: return

                if (action != Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
                    return
                }

                val homeKey = intent.getStringExtra(SYSTEM_REASON)
                if (SYSTEM_HOME_KEY != homeKey) {
                    return
                }

                if (System.currentTimeMillis() - lastClickTime < 200) {
                    lastClickTime = System.currentTimeMillis()
                    return
                }
                lastClickTime = System.currentTimeMillis()

                Log.d(TAG, "onReceive() called with: context = $context, intent = $intent")
            }
        }, IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
        btnText.setOnClickListener {
            val value = UUID.randomUUID()
            Log.d(TAG, "onCreate() called value =$value")
            val intent = Intent(this, HomekeyAActivity::class.java)
            intent.putExtra("value", value)
            startActivity(intent)
        }
    }

}