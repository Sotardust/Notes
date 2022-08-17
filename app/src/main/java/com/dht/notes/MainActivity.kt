package com.dht.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.notes.code.toast.ToastActivity
import com.dht.notes.code.activity.AActivity
import com.dht.notes.code.adapter.MainAdapter
import com.dht.notes.code.animation.AnimationActivity
import com.dht.notes.code.animation.WifiWaveActivity
import com.dht.notes.code.coordlayout.CoorActivity
import com.dht.notes.code.floatingwindow.FloatingWindowActivity
import com.dht.notes.code.homekey.HomekeyAActivity
import com.dht.notes.code.lock.ThreadLockActivity
import com.dht.notes.code.provider.ProviderActivity
import com.dht.notes.code.service.ServiceActivity
import com.dht.notes.code.shake.ShakeActivity
import com.dht.notes.code.shake.ShakeSensorListener
import com.dht.notes.code.telephony.TelephonyActivity
import com.dht.notes.code.utils.VerticalDecoration
import com.dht.notes.code.view.FlowActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

/**
 * created by Administrator on 2019/5/28 18:46
 */
class MainActivity : Activity() {

    private val TAG = "TestDispatchers"
    private val activityList: MutableList<KClass<*>> = mutableListOf()

    private var shakeListener = object : ShakeSensorListener() {
        override fun onSensorChanged(event: SensorEvent?) {

            Log.d("TAG11", "hook() onSensorChanged() called with: event = $event")

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            testDispatchers()
        }

        val list = arrayListOf(
            "摇一摇",
            "toast测试",
            "HomeKey测试",
            "悬浮球测试",
            "sim卡信息",
            "Scroll",
            "水波纹",
            "自定义View",
            "线程锁",
            "activity 生命周期跳转",
            "Animation 动画属性",
            "service aidl 服务",
            "ContentProvider 内容提供者"
        )
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(VerticalDecoration(5))
        val adapter = MainAdapter(list)
        recyclerView.adapter = adapter
        addActivityList()

        adapter.setOnItemClickListener { adapter, view, position ->
            // testDispatchers()
            startActivity(Intent(this@MainActivity, activityList[position].java))
        }
        val myAsyncTask = MyAsyncTask()
        myAsyncTask.execute("fdsa")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }


    override fun onDestroy() {
        super.onDestroy()
        //取消注册
        sensorManager.unregisterListener(shakeListener)
    }

    fun log(msg: String) = println("TestDispatchers [${Thread.currentThread().name}] $msg")

    private fun testDispatchers() = runBlocking {

        Log.d(TAG, "main                 : I'm working in thread ${Thread.currentThread().name} ")

        launch(Dispatchers.IO) {
            Log.d(TAG, "launch Default       : I'm working in thread ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.Default) {
            Log.d(TAG, "withContext Default  : I'm working in thread ${Thread.currentThread().name}")
        }
    }

    private fun testDispatchers1() = runBlocking {

        log("main             : I'm working in thread ${Thread.currentThread().name}")

        launch(Dispatchers.IO) {

            log("launch IO        : I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            log("launch Default   : I'm working in thread ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.Unconfined) {
            log("Unconfined1      : I'm working in thread ${Thread.currentThread().name} ")
        }

        withContext(Dispatchers.IO) {

            delay(5000)
            log("IO               : I'm working in thread ${Thread.currentThread().name} ")

            withContext(Dispatchers.Unconfined) {
                log("Unconfined2      : I'm working in thread ${Thread.currentThread().name}")
            }
        }

        withContext(Dispatchers.Default) {
            log("Default          : I'm working in thread ${Thread.currentThread().name} ")
        }
    }

    private fun addActivityList() {
        activityList.add(ShakeActivity::class)
        activityList.add(ToastActivity::class)
        activityList.add(HomekeyAActivity::class)
        activityList.add(FloatingWindowActivity::class)
        activityList.add(TelephonyActivity::class)
        activityList.add(CoorActivity::class)
        activityList.add(WifiWaveActivity::class)
        activityList.add(FlowActivity::class)
        activityList.add(ThreadLockActivity::class)
        activityList.add(AActivity::class)
        activityList.add(AnimationActivity::class)
        activityList.add(ServiceActivity::class)
        activityList.add(ProviderActivity::class)
    }


    companion object {
        private const val TAG = "dht"
    }

    class MyAsyncTask : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }


        override fun onProgressUpdate(vararg values: String) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
        }

        override fun onCancelled(s: String?) {
            super.onCancelled(s)
        }

        override fun doInBackground(vararg params: String): String {
            return ""
        }
    }

}