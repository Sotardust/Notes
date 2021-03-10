package com.dht.notes.code;

public class Singleton {


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

            System.out.println("count = " + (++count) + " this = " + this);
        }
    }
}
