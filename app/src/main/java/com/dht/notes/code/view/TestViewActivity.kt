package com.dht.notes.code.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Display
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.dht.notes.R
import com.ihs.app.framework.HSApplication
import com.thanosfisherman.wifiutils.WifiUtils
import kotlinx.android.synthetic.main.activity_text_view.*
import kotlinx.android.synthetic.main.activity_text_view.view.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * created by dht on 2021/11/12 17:31
 */
class TestViewActivity : Activity() {

    private val TAG = "TestViewActivity.TAG"

    private lateinit var wifiManager: WifiManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)
        val button = findViewById<TextView>(R.id.btnText)
        var width = 100

        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = true;

        button.setOnClickListener { v ->
            Log.d("TAG", "onClick() called with: v = [$v]")
            test2.text = getHandSetInfo()
            // test2.text ="""
            //     ${ getProp("ro.build.date")}
            //     ${ getProp("ro.build.date.utc")}
            //     ${ getProp("ro.com.android.date")}
            //     ${ getProp("ro.com.android.dateformat")}
            // """

        }
        getDetailsWifiInfo()

        val filter = IntentFilter()
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(broadcastReceiver, filter)

        wifiManager.startScan()




        WifiUtils.withContext(getApplicationContext()).scanWifi(this::getScanResults).start();

    }

    private fun getScanResults(results: List<ScanResult>) {

        if (results.isEmpty()) {
            Log.i(TAG, "SCAN RESULTS IT'S EMPTY");
            return;
        }

        for (result in results) {
            Log.d(TAG, "result = $result")
        }
    }

    private fun getHandSetInfo(): String {
        val dis: Display = this.windowManager.defaultDisplay
        // val d: Double = c.setScale(0, BigDecimal.ROUND_HALF_UP).toDouble()
        return """
            产品型号:${Build.MODEL}
            安卓版本:${Build.VERSION.RELEASE}
            内存:${Build.PRODUCT}
            分辨率:${dis.getWidth()}×${dis.getHeight()}
            版本显示:${Build.DISPLAY}
            时间:${Build.TIME}
            时间1:${getProp("ro.build.date")}}
            时间2:${getProp("ro.build.date.utc")}
            时间3:${getProp("ro.com.android.date")}
            时间4:${System.getProperty("ro.com.android.dateformat")}
         
            系统定制商:${Build.BRAND}
            设备参数:${Build.DEVICE}
            开发代号:${Build.VERSION.CODENAME}
            SDK版本号:${Build.VERSION.SDK_INT}
            硬件类型:${Build.HARDWARE}
            主机:${Build.HOST}
            生产ID:${Build.ID}
            ROM制造商:${Build.MANUFACTURER}
            """.trimIndent()
    }

    fun getProp(name: String): String? {
        var line: String? = null
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $name")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            Log.d(TAG, "getProp() called with: name = $name")
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return line
    }

    @SuppressLint("MissingPermission")
    fun getDetailsWifiInfo() {
        val sInfo = StringBuffer()
        val mWifiManager: WifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val mWifiInfo: WifiInfo = mWifiManager.getConnectionInfo()
        val Ip: Int = mWifiInfo.getIpAddress()
        val strIp = "" + (Ip and 0xFF) + "." + (Ip shr 8 and 0xFF) + "." + (Ip shr 16 and 0xFF) + "." + (Ip shr 24 and 0xFF)
        sInfo.append(
            """
            
            --BSSID : ${mWifiInfo.getBSSID()}
            """.trimIndent()
        )
        sInfo.append(
            """
            --SSID : ${mWifiInfo.getSSID()}
            """.trimIndent()
        )
        sInfo.append("\n--nIpAddress : $strIp")
        sInfo.append(
            """
            
            --MacAddress : ${mWifiInfo.getMacAddress()}
            """.trimIndent()
        )
        sInfo.append(
            """
            
            --NetworkId : ${mWifiInfo.getNetworkId()}
            """.trimIndent()
        )
        sInfo.append(
            """
            
            --LinkSpeed : ${mWifiInfo.getLinkSpeed().toString()}Mbps
            """.trimIndent()
        )
        sInfo.append(
            """
            
            --Rssi : ${mWifiInfo.getRssi()}
            """.trimIndent()
        )
        sInfo.append(
            """
            
            --SupplicantState : ${mWifiInfo.getSupplicantState()}$mWifiInfo
            """.trimIndent()
        )
        sInfo.append("\n\n\n\n")
        Log.d(TAG, sInfo.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val scanResults: List<ScanResult> = wifiManager.scanResults
            if (ActivityCompat.checkSelfPermission(this@TestViewActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            val list = wifiManager.configuredNetworks
            Log.d(TAG, " list = ${list}")
            for (value in list) {
                Log.d(TAG, " value = ${value}")
            }

            for (result in scanResults) {
                Log.d(TAG, "onReceive() called with: SSID = ${result.SSID} BSSID = ${result.BSSID} level = ${result.level}")
            }
        }
    }
}