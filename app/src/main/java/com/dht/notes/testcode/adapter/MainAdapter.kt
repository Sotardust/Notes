package com.dht.notes.testcode.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dht.notes.R


/**
 *  巡检项 测振，测气LinearLayout布局
 *
 *  created by dht on 2020/1/14 15:00
 */

class MainAdapter : BaseQuickAdapter<String, BaseViewHolder> {

    constructor(data: MutableList<String>) : super(R.layout.item_main, data)

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.item_content, item)

    }

}