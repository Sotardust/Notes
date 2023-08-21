package com.dht.notes.code.provider

import android.app.Activity
import android.content.ContentValues
import android.database.ContentObservable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.dht.notes.R
import kotlinx.android.synthetic.main.activity_provider.*

/**
 * created by Administrator on 2019/10/11 11:05
 */
class ProviderActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)
        val values = ContentValues()
        values.put("姓名", "代海涛")
        values.put("年龄", 23)
        val uri = Uri.parse("content://com.dht.notes.code.provider/user")
        val sb = StringBuilder()
        providerInsert.setOnClickListener {
            contentResolver.insert(uri, values)
        }
        providerDelete.setOnClickListener {
            contentResolver.delete(uri, null, null)
        }
        providerUpdate.setOnClickListener {
            contentResolver.update(uri, values, null, null)
        }
        providerQuery.setOnClickListener {
            val result = contentResolver.query(uri, null, null, null, null)

            Log.d(TAG, "onCreate: ${result?.moveToNext()}")

        }

    }

    companion object {
        private const val TAG = "dht"
    }
}