package com.dht.notes.code.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.delay as delay1

object KotlinTest {
    @JvmStatic
    fun main(args: Array<String>) {
      test()
    }

    fun test() = runBlocking {
        println("........................")
        GlobalScope.launch {

            println("1........................")
            println("1........................")

            println("1........................")
            val count = repeat()
            val data = count + 5

            println("3........................")
            println("3................ewrq........")

            println("data = ${data}")

        }
    }


    suspend fun repeat(): Int {
        return suspendCoroutine{
            var count =0
            for (i in 0..10000){
                count++
            }
            println(" 300011111111111111")
            println(" 3000111111111111C9")


            count
        }

    }
}




