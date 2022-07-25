package com.dht.notes.code.lock;

import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {

    private static final String TAG = "FooBar";

    private final int n;

    private AtomicInteger count = new AtomicInteger(0);
    private final Lock lock = new ReentrantLock();
    private Condition conditionFoo = lock.newCondition();
    private Condition conditionBar = lock.newCondition();
    private LinkedBlockingDeque<Integer> foo = new LinkedBlockingDeque<>(1);
    private LinkedBlockingDeque<Integer> bar = new LinkedBlockingDeque<>(1);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        foo.put(1);
        for (int i = 0; i < n; i++) {
            foo.take();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            bar.put(i);

        }
        System.out.println("printFoo = " + printFoo);
    }

    public void bar(Runnable printBar) throws InterruptedException {
        System.out.println("count.intValue() = " + count.intValue());
        for (int i = 0; i < n; i++) {
            bar.take();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            foo.put(1);
        }
        System.out.println("printBar = " + printBar);
    }
}