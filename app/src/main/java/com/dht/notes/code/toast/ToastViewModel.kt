package com.dht.notes.code.toast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * created by dht on 2022/8/18 14:24
 */
class ToastViewModel:ViewModel() {

    val listLiveData:MutableLiveData<List<String>> = MutableLiveData()

}