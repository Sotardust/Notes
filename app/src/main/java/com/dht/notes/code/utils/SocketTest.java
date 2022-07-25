package com.dht.notes.code.utils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketTest {

    private static final String TAG = "SocketTest";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("172.16.4.64", 8084);
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            for (int i = 0; i < 2000; i++) {

                Log.d(TAG, "main: ");
                Thread.sleep(1000);
                br.write(" i =" + i);
                br.newLine();
//                br.flush();
                System.out.println("发送数据 i = " + i + socket.isConnected());
            }

            br.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
