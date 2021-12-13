package com.dht.notes.code.toast

import android.app.Activity
import android.os.Bundle
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_test_toast.*

/**
 * created by dht on 2021/12/13 09:37
 */
class ToastActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toast)
        val adToast = CustomRewardAdToast(this)
        val customToast = CustomToast(this,null)
        showBtn.setOnClickListener {
            adToast.showToast()
            // customToast.show(10000)
            // ohter.show("fdafsfsf测试数据",10000)

        }
        cancelBtn.setOnClickListener {
            adToast.cancel()
            // customToast.show(10000)
            // ohter.show("fdafsfsf测试数据",10000)

        }
    }
}