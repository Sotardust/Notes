package com.dht.notes.testcode.ontouch.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class ScreenUtil {

    public static int WIDTH = 0;
    public static int HEIGHT = 0;

    public static void setScreenWidthHeight(Context content) {

        Point point = new Point();
        WindowManager windowManager = (WindowManager) content.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(point);
        WIDTH = point.x;
        HEIGHT = point.y;
    }
}
