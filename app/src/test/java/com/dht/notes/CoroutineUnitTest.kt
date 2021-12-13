package com.dht.notes

import android.util.Log
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("UNREACHABLE_CODE")
class CoroutineUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMain() = runBlocking<Unit> {

        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")

        launch(Dispatchers.IO) {
            println("launch(Dispatchers.IO)     : I'm working in thread ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.Default) {
            println("Default   : I'm working in thread ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.IO) {
            println("Default   : I'm working in thread ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.Unconfined) {
            println("Unconfined   : I'm working in thread ${Thread.currentThread().name}")
        }

        withContext(Dispatchers.Main) {
            println("Main   : I'm working in thread ${Thread.currentThread().name}")
        }
    }

    @Test
    fun testDD() {
        val sortedMap: SortedMap<Int, String> = TreeMap()
        sortedMap[10] = "a"
        sortedMap[25] = "b"
        sortedMap[12] = "c"
        sortedMap[33] = "d"
        sortedMap[20] = "e"

        for (entry in sortedMap.entries.reversed()) {
            println("entry.key = ${entry.key} entry.value = ${entry.value}")
        }
    }


    @Test
    fun testDE() = runBlocking {


        withContext(Dispatchers.IO){

        }

        println("start ...... = " + System.currentTimeMillis())
        val value = getValue()

        println("value = ${value} , " + System.currentTimeMillis())

    }


    suspend fun getValue(): String {
        delay(1000)
        return "测试数据"
    }


    @Test
    fun testBBB() {
        val list: MutableList<String> = arrayListOf()
        list.add(Test01::class.java.name)
        list.add(Test02::class.java.name)
        println("list = $list")
    }
}
