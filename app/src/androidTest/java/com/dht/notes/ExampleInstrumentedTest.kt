package com.dht.notes

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.dht.notes", appContext.packageName)
    }

    @Test
    fun testDat() {
        val endDate = "2019-5-20"
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        try {
            val date = format.parse(endDate)
            format.format(date)
            Log.d("dht", "onInitData: ")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }
}
