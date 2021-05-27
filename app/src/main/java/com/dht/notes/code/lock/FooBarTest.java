package com.dht.notes.code.lock;

import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FooBarTest {

    private static final String TAG = "dht";

    FooBar fooBar = new FooBar(2);

    public static void main(String[] args) {

//        try {
//            fooBar.foo(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("foo");
//                }
//            });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        try {
//
//            Log.d(TAG, "main: ");
//            fooBar.bar(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("bar");
//                }
//            });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("testInt() = " + testInt());
    }


    TestABC testABC = new TestABC();

    public static int testInt() {
        int count = 0;
        try {
            System.out.println(" ......... = " + count);
            return ++count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(" ......... = " + count);
            return count;
        }
    }

    class TestABC {
        void tes() {
//            fooBar
        }
    }

}