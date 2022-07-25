package com.dht.notes.code.lock;

public class TestDemo {

    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();
        Object object = new Object();
        System.out.println(threadName + " start.");
        BThread bt = new BThread();
        AThread at = new AThread(bt);
        try {
            bt.start();
//            Thread.sleep(2000);
            at.start();
            at.join();
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(threadName + " end!");
    }
}
