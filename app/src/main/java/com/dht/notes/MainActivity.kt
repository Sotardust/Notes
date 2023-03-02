package com.dht.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewCacheExtension
import com.dht.notes.code.activity.AActivity
import com.dht.notes.code.adapter.MainAdapter
import com.dht.notes.code.animation.AnimationActivity
import com.dht.notes.code.animation.WifiWaveActivity
import com.dht.notes.code.coordlayout.CoorActivity
import com.dht.notes.code.floatingwindow.FloatingWindowActivity
import com.dht.notes.code.homekey.HomekeyAActivity
import com.dht.notes.code.lock.ThreadLockActivity
import com.dht.notes.code.provider.ProviderActivity
import com.dht.notes.code.retrofit.GitHubService
import com.dht.notes.code.service.ServiceActivity
import com.dht.notes.code.shake.ShakeActivity
import com.dht.notes.code.shake.ShakeSensorListener
import com.dht.notes.code.telephony.TelephonyActivity
import com.dht.notes.code.toast.ToastActivity
import com.dht.notes.code.utils.GridDecoration
import com.dht.notes.code.view.FlowActivity
import com.dht.notes.code.view.TouchEventActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.reflect.KClass


/**
 * created by Administrator on 2019/5/28 18:46
 */
class MainActivity : Activity() {

    private val TAG = "TestDispatchers"
    private val activityList: MutableList<KClass<*>> = mutableListOf()

    private var shakeListener = object : ShakeSensorListener() {
        override fun onSensorChanged(event: SensorEvent?) {

            //ZygotePreload
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

        }

        val list = arrayListOf(
            "事件分发处理",
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
        recyclerView.layoutManager = GridLayoutManager(baseContext, 3)
        recyclerView.addItemDecoration(GridDecoration(10))
        val adapter = MainAdapter(list)
        recyclerView.adapter = adapter
        addActivityList()

        recyclerView.setViewCacheExtension(object :ViewCacheExtension(){
            override fun getViewForPositionAndType(
                recycler: RecyclerView.Recycler,
                position: Int,
                type: Int
            ): View? {
                TODO("Not yet implemented")
            }

        })

        adapter.setOnItemClickListener { adapter, view, position ->
            // testDispatchers()
            startActivity(Intent(this@MainActivity, activityList[position].java))
        }
        val myAsyncTask = MyAsyncTask()
        myAsyncTask.execute("fdsa")


//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//
//        sensorManager.registerListener(shakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.github.com/")
//            .addCallAdapterFactory()
//            .addConverterFactory()
//            .addConverterFactory()
//            .build()
//        val service = retrofit.create(GitHubService::class.java)
//
//        val repos: Call<List<String>> = service.listRepos("octocat")
//
//        val call  =repos.cancel()
//        repos.enqueue(object :Callback<List<String>>{
//
//            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
//
//            }
//
//            override fun onFailure(call: Call<List<String>>, t: Throwable) {
//
//            }
//
//        })

    }


    override fun onDestroy() {
        super.onDestroy()
        //取消注册
//        sensorManager.unregisterListener(shakeListener)
    }
    private fun addActivityList() {
        activityList.add(TouchEventActivity::class)
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