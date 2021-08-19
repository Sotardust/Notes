package com.dht.notes.code.telephony

object SceneAdUtils {

    const val TAG = "SceneAdUtils"

    val interstitialAdWrapperMap: MutableMap<String, List<String>> = mutableMapOf()

    val adWrapperViewMap: MutableMap<String, MutableList<InterstitialAdEcpm>?> = mutableMapOf()

    data class InterstitialAdEcpm(val adWrapper: String, val ecpm: Float)

}