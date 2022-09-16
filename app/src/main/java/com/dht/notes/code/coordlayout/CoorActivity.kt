package com.dht.notes.code.coordlayout

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dht.notes.R
import com.dht.notes.code.adapter.MainAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_coor.*
import kotlinx.android.synthetic.main.activity_coor_copy.*
import java.util.*
import kotlin.math.abs

class CoorActivity : FragmentActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coor)

        // app_bar_layout.addOnOffsetChangedListener { appBarLayout: AppBarLayout?, verticalOffset: Int ->
        //     Log.d(TAG, "onCreate() called with: appBarLayout = $appBarLayout, verticalOffset = $verticalOffset")
        // }

        var height = 0f
        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout> {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (height == 0f) {
                    height = (app_bar_layout.height - new_toolbar.height).toFloat()
                    return
                }

                val value = abs(verticalOffset) / height
                new_toolbar.alpha = if (value < 0.3f) 0f else value

                Log.d(TAG, "onOffsetChanged() abs(verticalOffset) % height :${abs(verticalOffset) / height} with:${app_bar_layout.height} toolbar:${new_toolbar.height}  verticalOffset = $verticalOffset")
            }

        })

    }


    fun getAlpha(percent: Float): Int {
        return (255 * (1 - percent)).toInt()
    }

    private fun getTabView(i: Int): View? {
        val text = TextView(this)
        text.text = "tab$i"
        return text
    }

    companion object {
        private const val TAG = "dht"
    }
}