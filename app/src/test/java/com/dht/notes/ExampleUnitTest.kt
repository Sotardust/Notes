package com.dht.notes

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
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

}
