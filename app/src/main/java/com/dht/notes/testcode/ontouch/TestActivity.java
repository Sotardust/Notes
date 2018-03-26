package com.dht.notes.testcode.ontouch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dht.notes.MainActivity;
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

        Log.d(TAG, "onCreate() returned: " + Thread.currentThread().getId());
        System.out.println("android.os.Process.myPid()   = " + android.os.Process.myPid()  );
        Log.d(TAG, "Looper.getMainLooper().getThread() " + Looper.getMainLooper().getThread().getId());

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");

                System.out.println("android.os.Process.myPid()   = " + android.os.Process.myPid()  );
            }
        });
        thread.start();
        HandlerThread handlerThread = new HandlerThread("hkh");
        Log.d(TAG, " thread.getId() returned: " + thread.getId());


//        Log.i(TAG, "onCreate: ");
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick(): linearLayout");
//            }
//        });
//
//        final int[] count = {0};
//        notes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick(): notes ");
//                Toast.makeText(TestActivity.this, "点击" + count[0]++ + "次数", Toast.LENGTH_SHORT).show();
//            }
//        });
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: test1");
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
//        test2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: test2");
//            }
//        });
//
//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        } ;
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
