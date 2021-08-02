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

                Log.d(TAG, "onOffsetChanged() called abs(verticalOffset) % height :${abs(verticalOffset) / height} with:${app_bar_layout.height} toolbar:${new_toolbar.height}  verticalOffset = $verticalOffset")
            }

        })

        // news_tool_bar_layout.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        //
        //     Log.d("dht tool_bar_layout", "onCreate() called with: v = $v, scrollX = $scrollX, scrollY = $scrollY, oldScrollX = $oldScrollX, oldScrollY = $oldScrollY")
        // }
        // app_bar_layout.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        //
        //     Log.d("dht app_bar_layout", "onCreate() called with: v = $v, scrollX = $scrollX, scrollY = $scrollY, oldScrollX = $oldScrollX, oldScrollY = $oldScrollY")
        // }
        // new_coordinator_layout.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        //
        //     Log.d("dht coordinator_layout", "onCreate() called with: v = $v, scrollX = $scrollX, scrollY = $scrollY, oldScrollX = $oldScrollX, oldScrollY = $oldScrollY")
        // }

        // nestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
        //     override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        //         Log.d("dht", "onScrollChange() called with:, scrollX = $scrollX, scrollY = $scrollY, oldScrollX = $oldScrollX, oldScrollY = $oldScrollY")
        //         val headView = v.findViewById<RelativeLayout>(R.id.rl_head)
        //         val content = v.findViewById<RelativeLayout>(R.id.rl_content)
        //         Log.d("dht", "onScrollChange() called with: .width = ${headView.width}, height = ${headView.height}, alpha = ${headView.alpha}, oldScrollY = ${headView.width}")
        //         Log.d("dht", "onScrollChange() called with: .width = ${content.width}, height = ${content.height}, alpha = ${content.alpha}, oldScrollY = ${headView.width}")
        //         if (oldScrollY> headView.height){
        //             // nestedScrollView.canScrollVertically(false)
        //         }
        //
        //     }
        //
        // })
        //
        // // val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        // val list: MutableList<String> = ArrayList()
        // for (i in 0..49) {
        //     list.add( " 数据 = " + i)
        // }
        // val mainAdapter = MainAdapter(list)
        // recyclerView.adapter = mainAdapter
        // recyclerView.layoutManager = LinearLayoutManager(this)
        // recyclerView.isNestedScrollingEnabled= false

        // val arrays = arrayListOf("tab0", "tab1", "tab2", "tab3", "tab4", "tab5")
        //
        // val fragments: MutableList<Fragment> = arrayListOf()
        // for (i in arrays.indices) {
        //
        //     val f = Fragment1()
        //     f.arguments = Bundle().apply { putString("key", arrays[i]) }
        //     fragments.add(f)
        //     val tab: TabLayout.Tab = tabLayout.newTab()
        //     tab.customView = getTabView(i)
        //     tabLayout.addTab(tab)
        // }
        //
        // viewPager2.adapter = BaseFragmentAdapter(fragments, this, arrays.toList())
        // viewPager2.currentItem = 1
        // tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        //     override fun onTabReselected(tab: TabLayout.Tab?) {
        //         Log.d(Companion.TAG, "onTabReselected() called with: tab = $tab")
        //     }
        //
        //     override fun onTabUnselected(tab: TabLayout.Tab?) {
        //         Log.d(TAG, "onTabUnselected() called with: tab = $tab")
        //     }
        //
        //     override fun onTabSelected(tab: TabLayout.Tab?) {
        //         Log.d(TAG, "onTabSelected() called with: tab = $tab")
        //         viewPager2.currentItem = tab!!.position
        //     }
        //
        // })
        // viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        //     override fun onPageSelected(position: Int) {
        //         super.onPageSelected(position)
        //         tabLayout.getTabAt(position)?.select()
        //         Log.d(TAG, "onPageSelected() called with: position = $position")
        //     }
        // })

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