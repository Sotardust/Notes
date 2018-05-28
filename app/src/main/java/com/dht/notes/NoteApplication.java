package com.dht.notes;

import android.app.Application;

import com.dht.notes.testcode.ontouch.util.ScreenUtil;

/**
 * Created by dai on 2018/3/29.
 */

public class NoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtil.setScreenWidthHeight(getApplicationContext());
    }
}
