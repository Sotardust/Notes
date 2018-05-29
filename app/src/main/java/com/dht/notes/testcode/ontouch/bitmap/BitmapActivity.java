package com.dht.notes.testcode.ontouch.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.dht.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/5/24.
 */

public class BitmapActivity extends AppCompatActivity {

    private static final String TAG = "BitmapActivity";
    @BindView(R.id.bitmap_image)
    ImageView bitmapImage;
    Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_bitmap);
        ButterKnife.bind(this);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.image_14);
        Bitmap bm = BitmapUtil.readBitmapFromResource(getApplicationContext());
//        Log.d(TAG, "onCreate() returned: 内存大小" + (bm.getByteCount())/(1024*1024));
//        bitmap = BitmapUtil.zoomBitmap(bm,300,300);
        bitmap =  BitmapUtil.getRoundedCornerBitmap(bm,300);
        Log.d(TAG, "onCreate() returned: bitmap " + bitmap);
        Log.d(TAG, "onCreate() returned: 内存大小" + (bitmap.getByteCount())/(1024*1024));
//        bitmapImage.setImageBitmap(BitmapUtil.bitmapScale(bitmap,2));
        bitmapImage.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null)
            bitmap = null;

    }
}
