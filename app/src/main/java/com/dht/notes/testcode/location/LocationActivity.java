package com.dht.notes.testcode.location;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.dht.notes.R;

/**
 * created by Administrator on 2019/10/21 15:08
 */
public class LocationActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        GasAndLocationUtil.INSTANCE.init(this);
        GasAndLocationUtil.INSTANCE.startUploadData(this);
        TextView start = findViewById(R.id.start);
        TextView stop = findViewById(R.id.stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GasAndLocationUtil.INSTANCE.startUploadData(LocationActivity.this);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GasAndLocationUtil.INSTANCE.stopUploadData();
            }
        });
    }
}
