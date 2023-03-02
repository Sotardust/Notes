package com.dht.notes.code.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

import leakcanary.AppWatcher;

/**
 * created by Administrator on 2019/10/11 11:07
 */

public class MyRemoteService extends Service {

    private MyService.MyBinder myBinder;
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = ((MyService.MyBinder) service);

            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
        }
    };

    private static final String TAG = "dht1";
    @Override
    public void onCreate() {
        super.onCreate();
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
        Log.d(TAG, "onStartCommand() called "+ Process.myPid()+" pro"+ Thread.currentThread().getName());

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
        bindService(new Intent(getApplicationContext(),MyRemoteService.class),connection,BIND_AUTO_CREATE);
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
