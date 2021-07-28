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
import androidx.annotation.RequiresApi
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_telephony.*

/**
 *  created by Administrator on 2020/9/8 17:20
 */

class TelephonyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telephony)

        phoneBtn.setOnClickListener {
            info.text = getInfo()
        }
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
                            list.add(cellIdentity.mobileNetworkOperator)

                        }
                        is CellInfoWcdma -> {
                            val cellIdentity: CellIdentityWcdma = cellInfo.cellIdentity
                            list.add(cellIdentity.mobileNetworkOperator)
                        }
                        is CellInfoLte -> {
                            val cellIdentity: CellIdentityLte = cellInfo.cellIdentity
                            list.add(cellIdentity.mobileNetworkOperator)
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
}
