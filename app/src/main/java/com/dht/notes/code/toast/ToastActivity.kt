package com.dht.notes.code.toast

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.dht.notes.R
import com.dht.notes.code.shake.ShakeSensorListener
import kotlinx.android.synthetic.main.activity_test_toast.*

/**
 * created by dht on 2021/12/13 09:37
 */
class ToastActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toast)
        val adToast = CustomRewardAdToast(this)
        val customToast = CustomToast(this, null)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ToastViewModel::class.java)
        viewModel.listLiveData.observe(this) {
            Log.d(Companion.TAG, "onCreate() called $it")
        }
        showBtn.setOnClickListener {
            // adToast.showToast()
            // customToast.show(10000)
            // ohter.show("fdafsfsf测试数据",10000)

            val list: MutableList<String> = mutableListOf()
            for (value in 0..10) {
                list.add("数据$value")
            }

            viewModel.listLiveData.value = list

            Log.d(Companion.TAG, "showBtn() called ${viewModel.listLiveData.value}")

        }
        cancelBtn.setOnClickListener {

            viewModel.listLiveData.value = null

            
            Log.d(Companion.TAG, "cancelBtn() called ${viewModel.listLiveData.value}")

            // adToast.cancel()
            // customToast.show(10000)
            // ohter.show("fdafsfsf测试数据",10000)

        }
    }

    companion object {
        private const val TAG = "ToastActivity"
    }

}