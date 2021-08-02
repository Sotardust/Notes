package com.dht.notes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("UNREACHABLE_CODE")
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testJava() {


//        while (true) {
//            val data = 5
//            if (data > 3) {
//                System.out.print("dht+onCreate: data = $data")
//                return
//            }
//        }

        var second = 1201
        val min = second / 60
        second %= 60
        var info = "距上次刷卡间隔小于"
        if (min < 1) {
            info += " $second 秒！"
        } else {
            info += "$min 分钟"
            if (second != 0) {
                info += " $second 秒！"
            }
        }
        print("info = $info")
    }

    @Test
    fun testJava1() {
        val c = Calendar.getInstance()
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        println("c.timeInMillis  =${c.timeInMillis/1000 }")


    }

    @Test
    fun testJava2() {

        val a = "12"
        var b = 1
        try {
            b = a.toInt()

        } catch (e: Exception) {
            print(" result $b ,e $e")

        } finally {
            print("result = $3")

        }
    }

}
