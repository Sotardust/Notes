package com.dht.notes;

public class Singleton {
    private Singleton() {
    }

    public static Singleton getSingleton() {
        return Inner.instance;
    }

    private static class Inner {
        private static final Singleton instance = new Singleton();
    }
}