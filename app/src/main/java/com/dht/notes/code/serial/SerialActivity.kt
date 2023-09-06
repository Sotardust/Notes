package com.dht.notes.code.serial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.serialport.SerialPort
import com.dht.notes.R
import com.dht.notes.code.activity.BActivity
import kotlinx.android.synthetic.main.activity_a.*
import java.io.File
import java.io.InputStream
import java.io.OutputStream


/**
 *  获取串口数据
 */

class SerialActivity : Activity() {

    private val TAG = "dht SerialActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial)

        btnText.text = "D跳转到B"
        btnText.setOnClickListener {
            startActivity(Intent(this, BActivity::class.java))
        }

        val serialPort =  SerialPort(File("/dev/ttyHS0"), 115200);

        val `in`: InputStream = serialPort.inputStream
        val out: OutputStream = serialPort.outputStream
// 使用可选参数配置初始化，可配置数据位、校验位、停止位 - 7E2(7数据位、偶校验、2停止位)
//        SerialPort serialPort = SerialPort .newBuilder(path, baudrate)
// 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
//    .parity(2)
// 数据位,默认8；可选值为5~8
//    .dataBits(7)
// 停止位，默认1；1:1位停止位；2:2位停止位
//    .stopBits(2)
//            .build();


    }

}