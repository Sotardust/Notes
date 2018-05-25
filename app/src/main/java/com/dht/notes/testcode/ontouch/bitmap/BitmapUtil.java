package com.dht.notes.testcode.ontouch.bitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.dht.notes.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dai on 2018/5/25.
 */

public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    /**
     * 读取Drawable资源文件图片返回Bitmap
     *
     * @param context
     * @return
     */
    public static Bitmap readBitmapFromResource(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        @SuppressLint("ResourceType")
        InputStream inputStream = context.getResources().openRawResource(R.drawable.image_1);
        BitmapFactory.decodeStream(inputStream,null,options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        Log.d(TAG, "readBitmapFromResource: srcWidth" + srcWidth);
        Log.d(TAG, "readBitmapFromResource: srcHeight" + srcHeight);
        int height = 200;
        int width = 200;

        options.inJustDecodeBounds = false;
        options.inSampleSize = getFitInSampleSize(width,height,options);
        System.out.println("options.inSampleSize = " + options.inSampleSize);
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static int getFitInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
            int widthRatio = Math.round((float) options.outWidth / (float) reqWidth);
            int heightRatio = Math.round((float) options.outHeight / (float) reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            return BitmapFactory.decodeStream(bais);
        } catch (OutOfMemoryError error) {
            Log.d(TAG, "compressImage() returned: OutOfMemoryError" + error);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 根据scale生成一张图片
     *
     * @param bitmap
     * @param scale  等比缩放值
     * @return
     */
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }


}
