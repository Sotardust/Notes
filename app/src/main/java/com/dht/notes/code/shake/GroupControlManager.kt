package com.dht.notes.code.shake

import android.os.Handler
import android.os.Looper
import com.customtracker.dataanalytics.DataAnalytics
import com.customtracker.dataanalytics.event.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ihs.commons.mmkv.HSMMKVFile

/**
 * created by dht on 2022/11/22 20:48
 */
object GroupControlManager {

    private const val REPORT_INTERVAL_TIME = 8 * 60 * 60 * 1000

    private const val MMKV_FILE_GROUP_CONTROL_INDICATORS = "GroupControlIndicators"

    private const val MMKV_KEY_LAST_REPORT_ES_TIME = "MMKV_KEY_LAST_REPORT_ES_TIME"

    private val hsmmkvFile = HSMMKVFile.create(MMKV_FILE_GROUP_CONTROL_INDICATORS)

    private val handler = Handler(Looper.getMainLooper())

    fun initStateReport() {

        val lastReportTime = hsmmkvFile.getLong(MMKV_KEY_LAST_REPORT_ES_TIME, -1)
        val diffValue = System.currentTimeMillis() - lastReportTime

        if (diffValue > REPORT_INTERVAL_TIME) {

            handler.post {

                hsmmkvFile.putLong(MMKV_KEY_LAST_REPORT_ES_TIME, System.currentTimeMillis())
            }
        } else {
            handler.postDelayed({

                hsmmkvFile.putLong(MMKV_KEY_LAST_REPORT_ES_TIME, System.currentTimeMillis())

            }, REPORT_INTERVAL_TIME - diffValue)
        }
    }

    // fun log(eventName: String?, paramMap: Map<String?, Any?>?) {
    //     if (!DataAnalytics.checkFailed(eventName)) {
    //         DataAnalytics.insertEvent(Event(eventName, paramMap))
    //     }
    // }

    fun stateLogEvent(eventName: String, paramMap: Map<String, Any>) {


        // paramMap.values
        // val beans:Map<String, Any> = mutableMapOf()
        // hsmmkvFile.getString(MMKV_KEY_MAKE_MONEY_RECORD_LIST, null)?.let {
        //     val typeToken = object : TypeToken<Map<String, Any>>() {}.type
        //     // beans.addAll(Gson().fromJson(it, typeToken))
        //     beans
        //     beans =
        // }
        //
        // beans.add(moneyBean)
        //
        // hsmmkvFile.putString(MMKV_KEY_MAKE_MONEY_RECORD_LIST, Gson().toJson(beans))

        DataAnalytics.log(eventName, paramMap)
    }
}