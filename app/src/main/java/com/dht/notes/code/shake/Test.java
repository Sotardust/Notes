package com.dht.notes.code.shake;

import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dht on 2022/8/11 15:51
 */
public class Test {


    public Test() {
    }

    static final class ABB {
        private final SensorEventListener mListener;

        public ABB() {
            mListener = new ShakeSensorListener();
        }
    }
}
