package com.dht.notes.testcode.ontouch.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * 图片内存缓存方法
 * 1、LRU指的是：近期最少使用的算法，当内存不足时会清除近期最少使用的图片
 * 2、LRU内部采用的是LinkedHashMap
 * DiskLruCache硬盘缓存
 * Created by Administrator on 2018/5/30 0030.
 */

public class BitmapCache {

    private static final String TAG = "BitmapCache";
    private static BitmapCache cache;
    private LruCache<String, Bitmap> lruCache;
//    DiskLruCache


    private BitmapCache() {
        int maxSize = (int) Runtime.getRuntime().maxMemory();
        android.util.Log.d(TAG, "BitmapCache() returned: maxSize = " + maxSize);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                Log.d(TAG, "sizeOf() returned: " + value.getByteCount());
                return value.getByteCount();
            }
        };
    }

    public static BitmapCache getInstance() {
        if (cache == null) {
            synchronized (BitmapCache.class) {
                if (cache == null) {
                    cache = new BitmapCache();
                }
            }
        }
        return cache;
    }

    /**
     * 从缓存中读取bitmap图片
     *
     * @param key url
     * @return bitmap
     */
    private Bitmap getBitmapFromCache(String key) {
        return lruCache.get(key);
    }

    private void putBitmapToCache(Bitmap bitmap, String key) {
        if (bitmap != null) {
            lruCache.put(key, bitmap);
        }
    }
}
