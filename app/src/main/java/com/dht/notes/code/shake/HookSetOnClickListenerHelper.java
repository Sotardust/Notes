package com.dht.notes.code.shake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okreflect.OkReflect;

/**
 * hook的辅助类
 * hook的动作放在这里
 */
public class HookSetOnClickListenerHelper {


    private static final String TAG = "HookSetOnClickListenerH";

    /**
     * hook的核心代码
     * 这个方法的唯一目的：用自己的点击事件，替换掉 View原来的点击事件
     */
    @SuppressLint("SoonBlockedPrivateApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void hook(Context context, SensorManager sensorManager) {//
        try {

            Reflection.exemptAll();

            @SuppressLint("PrivateApi")
            Class<?> systemListenerInfoClz = Class.forName("android.hardware.SystemSensorManager");// 这是内部类的表示方法

            Field f = systemListenerInfoClz.getDeclaredField("mSensorListeners");
            f.setAccessible(true);
            Object object = f.get(sensorManager);
            Log.d(TAG, "hook() called with: object = [" + object + "]");

            //完成
        } catch (Exception e) {
            Log.d(TAG, "点击事件被hook到了 e =" + e);//加入自己的逻辑

            e.printStackTrace();
        }
    }

    // 还真是这样,自定义代理类
    static class ProxyOnClickListener implements SensorEventListener {

        SensorEventListener listener;

        public ProxyOnClickListener(SensorEventListener oriLis) {
            this.listener = oriLis;
        }
        // SensorEvent t = new SensorEvent(3);

        @Override
        public void onSensorChanged(SensorEvent event) {

            float[] floats = new float[event.values.length];
            for (int i = 0; i < floats.length; i++) {
                floats[i] = event.values[i] + 20;
            }

            System.arraycopy(floats, 0, event.values, 0, event.values.length);

            listener.onSensorChanged(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    /**
     * 通过反射获取类的所有方法
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void printMethods() throws ClassNotFoundException {
        //1.获取并输出类的名称
        @SuppressLint("PrivateApi")
        Class mClass = Class.forName("android.hardware.SystemSensorManager");
        Log.d(TAG, " 类的名称：" + mClass.getName());

        //2.1 获取所有 public 访问权限的方法
        //包括自己声明和从父类继承的
        Method[] mMethods = mClass.getMethods();

        Log.d(TAG, "hook() printMethods() called mMethods" + mMethods);
        //2.2 获取所有本类的的方法（不问访问权限）
        //Method[] mMethods = mClass.getDeclaredMethods();

        //3.遍历所有方法
        for (Method method : mMethods) {
            //获取并输出方法的访问权限（Modifiers：修饰符）
            int modifiers = method.getModifiers();
            Log.d(TAG, Modifier.toString(modifiers) + " ");
            //获取并输出方法的返回值类型
            Class returnType = method.getReturnType();
            Log.d(TAG, returnType.getName() + " " + method.getName() + "( ");
            //获取并输出方法的所有参数
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                Log.d(TAG, parameter.getType().getName() + " " + parameter.getName() + ",");
            }
            //获取并输出方法抛出的异常
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length == 0) {
                Log.d(TAG, "  )");
            } else {
                for (Class c : exceptionTypes) {
                    Log.d(TAG, "  ) throws "
                            + c.getName());
                }
            }
        }
    }
}
