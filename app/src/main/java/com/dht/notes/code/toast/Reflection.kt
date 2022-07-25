package com.dht.notes.code.toast

import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * created by dht on 2021/12/13 12:32
 */
object Reflection {
    private const val TAG = "dht111"
    @Throws(Exception::class)
    fun test() {
        try {
            val clz = Toast::class.java
            //得到字节码对象
            // Class<?> clz = Class.forName("android.widget.Toast");
            //获取变量名为"NAME"的私有字段
            val field = clz.getDeclaredField("mTN")
            field.isAccessible = true
            val obj = field[clz.javaClass]
            Log.d(TAG, "test() called obj = $obj")
            val modifiers = Field::class.java.getDeclaredField("modifiers")
            modifiers.isAccessible = true
            //去掉final修饰符
            modifiers.setInt(field, field.modifiers and Modifier.FINAL.inv())
            println("修改前: " + field[null])
            field[null] = "BBB"
            println("修改后: " + field[null])

            //再把final修饰符给加回来
            modifiers.setInt(field, field.modifiers and Modifier.FINAL.inv())
        } catch (e: Exception) {
            Log.e(TAG, "test: e", e)
            e.printStackTrace()
        }
    }
}