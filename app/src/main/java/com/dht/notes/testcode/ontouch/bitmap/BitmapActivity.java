package com.dht.notes.testcode.ontouch.bitmap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_bitmap);
        ButterKnife.bind(this);
        bitmap = BitmapUtil.readBitmapFromResource(getApplicationContext());
        Log.d(TAG, "onCreate() returned: bitmap " + bitmap);
//        bitmapImage.setImageBitmap(BitmapUtil.bitmapScale(bitmap,2));
        bitmapImage.setImageBitmap(BitmapUtil.compressImage1(bitmap));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null)
            bitmap = null;

    }
}
