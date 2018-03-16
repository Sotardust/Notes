package com.dht.notes.testcode.ontouch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dht.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/2/8.
 */

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.notes)
    MyTextView notes;
    @BindView(R.id.linearLayout)
    MyLinearLayout linearLayout;
    @BindView(R.id.test1)
    TextView test1;
    @BindView(R.id.test2)
    TextView test2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i(TAG, "onCreate: ");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick(): linearLayout");
            }
        });

        final int[] count = {0};
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick(): notes ");
                Toast.makeText(TestActivity.this, "点击" + count[0]++ + "次数", Toast.LENGTH_SHORT).show();
            }
        });
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: test1");
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: test2");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
