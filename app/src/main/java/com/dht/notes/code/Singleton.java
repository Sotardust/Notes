package com.dht.notes.code;

import android.util.Log;

public class Singleton {


    private static final String TAG = "Singleton";

    private volatile static Singleton mSingleton;


    private int count;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (null == mSingleton) {
            synchronized (Singleton.class) {
                if (null == mSingleton) {
                    mSingleton = new Singleton();
                }
            }
        }
        return mSingleton;
    }

    public  void add() {
        synchronized (Singleton.class) {

            Log.d(TAG, "add: ");
            System.out.println("TAG = " + TAG);
        }
    }
}
