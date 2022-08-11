package com.dht.notes.code.telephony

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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

class TelephonyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telephony)

        phoneBtn.setOnClickListener {
            // info.text = getInfo()

            val list1 = TestList()
            list1.setTestCase(TestCase())
            println("list1.getAdPlacementList(1) = ${list1.getAdPlacementList(1)}")
            println("list1.getAdPlacementList(4) = ${list1.getAdPlacementList(4)}")
            println("list1.getAdPlacementList(5) = ${list1.getAdPlacementList(5)}")

            println("SceneAdUtils.adWrapperViewMap[\"fds\"]  = ${SceneAdUtils.adWrapperViewMap["fds"]}")

            SceneAdUtils.adWrapperViewMap["df"] = mutableListOf(
                SceneAdUtils.InterstitialAdEcpm("dff", 0.6f),
                SceneAdUtils.InterstitialAdEcpm("dff", 0.8f)
            )


            val list2 = SceneAdUtils.adWrapperViewMap["df"]
            // val resul1 = list2?.maxBy { it.ecpm }
            // list2?.remove(resul1)
            println("SceneAdUtils list2 = ${list2}")
            // println("SceneAdUtils resul1 = ${resul1}")
             val result  = mutableListOf("345","dfa","wdfas")
            val value  =  result[0]
            result.remove(value)
            println("result = ${result}")

            val value1= mutableListOf<String>()
            println("value1 = ${value1.isNullOrEmpty()}")
            println("result = ${result}")


        }
        phoneBtn1.setOnClickListener {
            // info.text = getInfo()

            val list1 = TestList()
            list1.setTestCase(TestCase())
            println("list1.getAdPlacementList(1) = ${list1.getAdPlacementList(1)}")
            println("list1.getAdPlacementList(4) = ${list1.getAdPlacementList(4)}")
            println("list1.getAdPlacementList(5) = ${list1.getAdPlacementList(5)}")
            val list22 = mutableListOf<String>()

            list22.add("fsa")
            println("SceneAdUtils list22 = ${list22.size}")
            list22.remove("fsa")

            println("SceneAdUtils list22.... = ${list22.size}")
            println("SceneAdUtils list22.... = ${list22.isNullOrEmpty()}")

            // println("SceneAdUtils.adWrapperViewMap[\";ffsds\"]  = ${SceneAdUtils.adWrapperViewMap["df"]?.maxBy { it.ecpm }!!.ecpm}")
            println("SceneAdUtils.adWrapperViewMap[\"fds\"] = ${SceneAdUtils.adWrapperViewMap["fds"].isNullOrEmpty()}")

        }

        GlobalScope.launch {
            val result2 = getInfo5000()

            Log.d(Companion.TAG, "onCreate: ")

            val result = getInfo3000()

            Log.d(TAG, "onCreate() called  result = ${result},result2 = $result2")
        }
    }


    private suspend fun getInfo3000(): String = suspendCoroutine {
        Log.d(TAG, "getInfo3000: 111111111111")
        runBlocking {
            try {
                delay(timeMillis = 3000)

                it.resume("123 30000")
            } catch (e: Exception) {
                e.printStackTrace()
                it.resume("123")
            }
        }

        Log.d(TAG, "getInfo3000: 3333333333333")

    }


    private suspend fun getInfo5000(): String = suspendCoroutine {
        Log.d(TAG, "getInfo5000: 111111111")
        runBlocking {
            try {
                delay(timeMillis = 5000)
                it.resume("345 50000")
            } catch (e: Exception) {
                e.printStackTrace()
                it.resume("345")

            }
        }
        Log.d(TAG, "getInfo5000: 33333333333")
    }

    @SuppressLint("MissingPermission", "NewApi")
    private fun getInfo(): String {

        val list: HashSet<String> = hashSetOf()
        val manager: TelephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var netExtraInfo = manager.simOperator
        if (null != manager.simOperator) {
            netExtraInfo = from(netExtraInfo)
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                val subscriptionManager: SubscriptionManager = this.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
                val activeSubscriptionInfoList: List<SubscriptionInfo> = subscriptionManager.activeSubscriptionInfoList
                for (subscriptionInfo in activeSubscriptionInfoList) {
                    val carrierName: CharSequence = subscriptionInfo.carrierName
                    list.add(carrierName.toString())
                }
                if (list.size == 2) {
                    return if (list.contains(netExtraInfo)) netExtraInfo else list.first()
                }
                if (list.size == 1) return list.first()
                return netExtraInfo
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                for (cellInfo in manager.allCellInfo) {
                    when (cellInfo) {
                        is CellInfoGsm -> {
                            val cellIdentity: CellIdentityGsm = cellInfo.cellIdentity
                            cellIdentity.mobileNetworkOperator?.let { list.add(it) }

                        }
                        is CellInfoWcdma -> {
                            val cellIdentity: CellIdentityWcdma = cellInfo.cellIdentity
                            cellIdentity.mobileNetworkOperator?.let { list.add(it) }
                        }
                        is CellInfoLte -> {
                            val cellIdentity: CellIdentityLte = cellInfo.cellIdentity
                            cellIdentity.mobileNetworkOperator?.let { list.add(it) }
                        }
                    }
                }
                val names: MutableList<String> = mutableListOf()
                for (code in list) {
                    names.add(from(code))
                }
                if (names.size == 0) return netExtraInfo
                if (names.size > 1) {
                    if (names.contains(netExtraInfo)) return netExtraInfo else names.first()
                } else {
                    return names.first()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return netExtraInfo
    }

    fun from(code: String) = when (code) {
        "46000", "46002", "46004", "46007", "46008" -> "中国移动"

        "46001", "46006", "46009" -> "中国联通"

        "46003", "46005", "46011" -> "中国电信"

        "46020" -> "中国铁通"

        else -> "其他网络"

    }

    companion object {
        private const val TAG = "dht123"
    }


}
