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


        while (true) {
            val data = 5
            if (data > 3) {
                System.out.print("dht+onCreate: data = $data")
                return
            }
        }
    }
}
