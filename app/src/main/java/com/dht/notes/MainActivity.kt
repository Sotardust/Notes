package com.dht.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dht.notes.testcode.adapter.MainAdapter
import com.dht.notes.testcode.utils.VerticalDecoration
import com.dht.notes.testcode.view.FlowActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * created by Administrator on 2019/5/28 18:46
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = arrayListOf("自定义View")
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(VerticalDecoration(10))
        val adapter = MainAdapter(list)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(this@MainActivity, FlowActivity::class.java))
                }
            }
        }
    }

}