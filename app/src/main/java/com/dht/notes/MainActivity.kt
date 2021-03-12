package com.dht.notes

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.notes.code.activity.AActivity
import com.dht.notes.code.adapter.MainAdapter
import com.dht.notes.code.animation.AnimationActivity
import com.dht.notes.code.lock.ThreadLockActivity
import com.dht.notes.code.utils.VerticalDecoration
import com.dht.notes.code.view.FlowActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.reflect.KClass

/**
 * created by Administrator on 2019/5/28 18:46
 */
class MainActivity : Activity() {

    private val activityList: MutableList<KClass<*>> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        linear.setOnTouchListener { _, event ->
//            Log.d(Companion.TAG, "onCreate: setOnTouchListener ${event.action}")
//            false
//        }

//        linear.setOnClickListener {
//            Log.d(Companion.TAG, "linear onCreate: setOnClickListener ")
//
//        }

//
//        textClick.setOnClickListener {
//            Log.d(Companion.TAG, "textClick onCreate: setOnClickListener ")
//
//        }
        val list = arrayListOf("自定义View", "线程锁", "生命周期跳转", "动画属性")
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(VerticalDecoration(10))
        val adapter = MainAdapter(list)
        recyclerView.adapter = adapter
        addActivityList()
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(Intent(this@MainActivity, activityList[position].java))
        }

        val myAsyncTask = MyAsyncTask()
        myAsyncTask.execute("fdsa")
    }

    private fun addActivityList() {
        activityList.add(FlowActivity::class)
        activityList.add(ThreadLockActivity::class)
        activityList.add(AActivity::class)
        activityList.add(AnimationActivity::class)
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