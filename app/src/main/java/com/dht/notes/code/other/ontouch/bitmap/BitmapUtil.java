package com.dht.notes.code.other.ontouch.bitmap;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.dht.notes.R;
import com.dht.notes.code.other.ontouch.util.ScreenUtil;

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
        InputStream inputStream = context.getResources().openRawResource(R.drawable.image_14);
        BitmapFactory.decodeStream(inputStream, null, options);
        int height = ScreenUtil.HEIGHT;
        int width = ScreenUtil.WIDTH;
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        Log.d(TAG, "readBitmapFromResource() returned: options.inSampleSize " + options.inSampleSize);
        try {
            //TODO 调用两次decodeStream方法造成输入流的位置改变导致bitmap返回值为null，需要重置，（会不会造成内存过多后面有待验证）
            inputStream.reset();
        } catch (Exception e) {
            Log.d(TAG, "readBitmapFromResource() returned: e " + e);
        }
        return BitmapFactory.decodeStream(inputStream, null, options);

    }

    private static int getFitInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options) {
//        int inSampleSize = 1;
//        if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
//            int widthRatio = Math.round((float) options.outWidth / (float) reqWidth);
//            int heightRatio = Math.round((float) options.outHeight / (float) reqHeight);
//            inSampleSize = Math.min(widthRatio, heightRatio);
//        }
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            long totalPixels = width / inSampleSize * height / inSampleSize;

            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
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
        matrix.postScale(scale, scale);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static Bitmap compressImage1(Bitmap image) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 20, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);
            return BitmapFactory.decodeStream(isBm);
        } catch (OutOfMemoryError e) {
            Log.d(TAG, "compressImage1() returned: OutOfMemoryError" + e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "compressImage1() returned: IOException" + e);
            }
        }
        return null;
    }

    //放大或者缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);// 长和宽放大缩小的比例
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    //对 bitmap 进行裁剪
    public Bitmap bitmapClip(Context context, int id, int x, int y) {
        Bitmap map = BitmapFactory.decodeResource(context.getResources(), id);
        map = Bitmap.createBitmap(map, x, y, 120, 120);
        return map;
    }


}
