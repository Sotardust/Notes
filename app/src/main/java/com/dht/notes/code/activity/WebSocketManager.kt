package com.dht.notes.code.activity

class WebSocketManager private constructor() {

    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebSocketManager()
        }
    }

    var dest = "hua"
    var src = "hua"
}
