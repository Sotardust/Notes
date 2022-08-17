package com.dht.notes.code.shake;

import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dht on 2022/8/11 15:51
 */
public class Test {


    private static int size = 3;

    private static class SystemServiceRegistry {

        static Object[] createServiceCache() {

            return new Object[size++];
        }
    }

    final Object[] mServiceCache = SystemServiceRegistry.createServiceCache();

    public int getSize() {
        return mServiceCache.length;
    }
}
