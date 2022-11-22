package com.dht.notes;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.dht.notes.code.other.ontouch.util.ScreenUtil;
import com.dht.notes.code.shake.HookSensorManager;

import com.ihs.app.framework.HSApplication;
/**
 * Created by dai on 2018/3/29.
 */

public class NoteApplication extends HSApplication {

    private static final String TAG = "dht";

    private static Application application;

    public static  Application getApplication() {
        return application;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ScreenUtil.setScreenWidthHeight(getApplicationContext());

        HookSensorManager.hook(this);

        //        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
        //            @Override
        //            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //                Log.d(TAG, "onActivityCreated: "+activity.getLocalClassName());
        //            }
        //
        //            @Override
        //            public void onActivityStarted(Activity activity) {
        //                Log.d(TAG, "onActivityStarted: "+activity.getLocalClassName());
        //            }
        //
        //            @Override
        //            public void onActivityResumed(Activity activity) {
        //                Log.d(TAG, "onActivityResumed: "+activity.getLocalClassName());
        //            }
        //
        //            @Override
        //            public void onActivityPaused(Activity activity) {
        //                Log.d(TAG, "onActivityPaused: "+activity.getLocalClassName());
        //            }
        //
        //            @Override
        //            public void onActivityStopped(Activity activity) {
        //                Log.d(TAG, "onActivityStopped: "+activity.getLocalClassName());
        //            }
        //
        //            @Override
        //            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //
        //            }
        //
        //            @Override
        //            public void onActivityDestroyed(Activity activity) {
        //                Log.d(TAG, "onActivityDestroyed: "+activity.getLocalClassName());
        //            }
        //        });

    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
        Log.d("dht", "registerActivityLifecycleCallbacks: ");

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initMMKV();
        initLauncherInfo();
    }
}
