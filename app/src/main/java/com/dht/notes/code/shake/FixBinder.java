package com.dht.notes.code.shake;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FixBinder implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    // 原来的Service对象 (IInterface)
    Object base;

    public FixBinder(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);//获取原接口的asInterface
            this.base = asInterfaceMethod.invoke(null, base); //使用原来的Binder反射执行获取本来服务的代理类
        } catch (Exception e) {
            throw new RuntimeException("hooked failed!");
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 欺骗系统,使之认为剪切版上一直有内容
        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }
        return method.invoke(base, args); //其余方法使用原Binder代理反射执行
    }
}