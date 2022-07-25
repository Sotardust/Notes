package com.dht.notes.code;

public class Test0302 {
    public static void main(String[] args) {

        int MAXIMUM_CAPACITY = 1 << 30 ;
        System.out.println("(2^32) = " + (1 << 31 ));
        System.out.println("(1 << 30) = " + (MAXIMUM_CAPACITY));
        System.out.println("(MAXIMUM_CAPACITY >>> 1) = " + (MAXIMUM_CAPACITY >>> 1));
        System.out.println("(MAXIMUM_CAPACITY *2) = " + ((MAXIMUM_CAPACITY >>> 1) *2));
    }


}
