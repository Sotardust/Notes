package com.dht.notes.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by Administrator on 2019/10/11 11:07
 */

public class MyService extends Service {


    private static final String TAG = "dht1";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved: ");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //执行多次
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //执行一次
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //执行一次
        Log.d(TAG, "onUnbind() called with: intent = [" + intent + "]");
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    static class MyBinder extends ServiceAidlInterface.Stub {

        AtomicInteger integer = new AtomicInteger();

        @Override
        public void add() throws RemoteException {
            integer.incrementAndGet();
        }

        @Override
        public int get() throws RemoteException {
            return integer.get();
        }
    }


}
