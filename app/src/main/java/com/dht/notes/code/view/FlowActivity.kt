package com.dht.notes.code.view

import android.app.Activity
import android.os.Bundle
import com.dht.notes.R

/**
 *  created by Administrator on 2020/9/8 17:20
 */

class FlowActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        val flowView: FlowView = findViewById(R.id.flow_view)
        val list: MutableList<String> = mutableListOf()
        list.add("测试数据1234")
        list.add("测试数34")
        list.add("据1234")
        list.add("测数据1234")
        list.add("数据1234")
        list.add("测试数据1234ok的")
        list.add("测试数234ok的")

        for(index in 0..100){
            list.add("数据 $index")
        }
        flowView.setInstanceData(list)
    }
}