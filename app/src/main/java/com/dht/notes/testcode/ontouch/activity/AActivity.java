package com.dht.notes.testcode.ontouch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.dht.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dai on 2018/4/23.
 */

public class AActivity extends Activity {
    private static final String TAG = "AActivity";
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_a);
        ButterKnife.bind(this);

//        test1();
//        test2();

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        Intent intent = new Intent(AActivity.this, BActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void test1() {
        synchronized (AActivity.class) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d(TAG, "test1() returned: " + AActivity.class);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }

    public void test2() {
        synchronized (this) {

            Log.d(TAG, "test2() returned: " + this);
        }
    }
}
