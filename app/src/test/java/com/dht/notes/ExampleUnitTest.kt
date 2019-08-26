package com.dht.notes

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

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


//        while (true) {
//            val data = 5
//            if (data > 3) {
//                System.out.print("dht+onCreate: data = $data")
//                return
//            }
//        }

        // 创建一个数值格式化对象

        val numberFormat = NumberFormat.getInstance()

        // 设置精确到小数点后2位

        numberFormat.maximumFractionDigits = 4

        val result = numberFormat.format((4f / 9f))
        print("result = $result")
    }

    @Test
    fun testJava2(): Int {

        val a = "12"
        var b = 1
        try {
            b = a.toInt()
            return b
        } catch (e: Exception) {
            print(" result $b ,e $e")
            return b
        } finally {
            print("result = $3")
            return 3
        }
    }

}
