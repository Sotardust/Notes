package com.dht.notes.code.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.dht.notes.R;

/**
 * created by dht on 2021/11/12 17:31
 */
public class OtherActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        TextView button = findViewById(R.id.btnText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick() called with: v = [" + v + "]");
            }
        });
    }
}