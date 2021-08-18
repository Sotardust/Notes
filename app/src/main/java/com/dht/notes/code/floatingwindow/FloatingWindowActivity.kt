package com.dht.notes.code.floatingwindow

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.telephony.CellIdentityCdma
import android.telephony.CellIdentityGsm
import android.telephony.CellIdentityLte
import android.telephony.CellIdentityWcdma
import android.telephony.CellInfoCdma
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_floating_window.*
import kotlinx.android.synthetic.main.activity_telephony.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *  created by Administrator on 2020/9/8 17:20
 */

class FloatingWindowActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_window)

        val datas = mutableListOf(
            CircularRingView.CircularRingElement(Color.parseColor("#1afa29"), 433f),
            CircularRingView.CircularRingElement(Color.parseColor("#b5a642"), 52f),
            CircularRingView.CircularRingElement(Color.parseColor("#d9d919"), 32f),
            CircularRingView.CircularRingElement(Color.parseColor("#ff0000"), 21f)
        )
        crv_attendance.setElementList(datas)
        crv_attendance.setRingThickness(crv_attendance.dp2px(20f))



    }


}
