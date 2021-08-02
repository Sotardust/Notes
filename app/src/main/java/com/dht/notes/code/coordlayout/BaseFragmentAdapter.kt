package com.dht.notes.code.coordlayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BaseFragmentAdapter : FragmentStateAdapter {

    private var fragmentList: List<Fragment>? = null
    private var contents: List<String>? = null

    constructor(
            fragmentList: List<Fragment>?,
            fragmentActivity: FragmentActivity,
            contents: List<String> = arrayListOf()
    )
            : super(fragmentActivity) {
        this.fragmentList = fragmentList
        this.contents = contents
    }

    constructor(
            fragmentList: List<Fragment>?,
            fragment: Fragment,
            contents: List<String> = arrayListOf()
    ) : super(fragment) {
        this.fragmentList = fragmentList
        this.contents = contents
    }

    override fun getItemCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList!![position]
    }

    fun getPageTitle(position: Int): String? {
        return contents?.get(position)
    }

    fun setData(fragmentList: List<Fragment>) {
        this.fragmentList = fragmentList
        notifyDataSetChanged()
    }
}
