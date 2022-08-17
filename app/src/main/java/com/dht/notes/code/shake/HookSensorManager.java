package com.dht.notes.code.shake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * hook的辅助类
 * hook的动作放在这里
 */
public class HookSensorManager {

    private static final String TAG = "HookSensorManager";

    private static final ArrayList<Object> systemSensorManagers = new ArrayList<>();

    /**
     * hook的核心代码
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void hook(Context context) {
        try {

            systemSensorManagers.clear();

            Reflection.exemptAll();

            @SuppressLint("PrivateApi")
            Class<?> registryClz = Class.forName("android.app.SystemServiceRegistry");

            @SuppressLint({ "SoonBlockedPrivateApi", "BlockedPrivateApi" })
            Field fetchers = registryClz.getDeclaredField("SYSTEM_SERVICE_FETCHERS");
            fetchers.setAccessible(true);

            ArrayMap<String, Object> arrayMap = (ArrayMap<String, Object>) fetchers.get(null);

            Object object = arrayMap.get(Context.SENSOR_SERVICE);

            @SuppressLint("PrivateApi")
            Class<?> cacheClz = Class.forName("android.app.SystemServiceRegistry$CachedServiceFetcher");

            Log.d(TAG, "hook() called with: getInterfaces = [" + cacheClz.getInterfaces() + "]");

            Object proxyObject = Proxy.newProxyInstance(context.getClass().getClassLoader(), cacheClz.getInterfaces(), (proxy, method, args) -> {

                Object systemSensorManager = method.invoke(object, args);

                systemSensorManagers.add(systemSensorManager);

                Log.d(TAG, "invoke() called with: obj = [" + systemSensorManager + "]");

                return systemSensorManager;//执行被代理的对象的逻辑
            });

            arrayMap.put(Context.SENSOR_SERVICE, proxyObject);

        } catch (Exception e) {
            Log.e(TAG, "hook: e", e);

            e.printStackTrace();
        }
    }

    public static void reflectSensorListenerList() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> systemListenerInfoClz = Class.forName("android.hardware.SystemSensorManager");

            @SuppressLint("SoonBlockedPrivateApi")
            Field f = systemListenerInfoClz.getDeclaredField("mSensorListeners");
            f.setAccessible(true);

            Log.d(TAG, "getSensorEventListener() called systemSensorManagers = " + systemSensorManagers.size());

            HashMap<SensorEventListener, Object> hashMap = new HashMap<>();

            for (Object object : systemSensorManagers) {
                hashMap.putAll((HashMap<SensorEventListener, Object>) f.get(object));
            }

            for (Map.Entry<SensorEventListener, Object> map : hashMap.entrySet()) {
                Log.d(TAG, "getSensorEventListener() called key = " + map.getKey() + ", Object = " + map.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getSensorEventListener: e", e);
        }
    }


}
