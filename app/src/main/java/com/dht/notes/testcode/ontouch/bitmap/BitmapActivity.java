package com.dht.notes.testcode.ontouch.bitmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.dht.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/5/24.
 */

public class BitmapActivity extends AppCompatActivity {
    @BindView(R.id.bitmap_image)
    ImageView bitmapImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_bitmap);
        ButterKnife.bind(this);
    }
}
