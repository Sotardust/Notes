package com.dht.notes.code.activity


/**
 * @author dht
 * @date 2023/3/23 14:48
 **/
open class WebSocketRequestBean<T> {

    val dest: String = WebSocketManager.INSTANCE.dest

    val src: String = WebSocketManager.INSTANCE.src

    var payload: T? = null

}