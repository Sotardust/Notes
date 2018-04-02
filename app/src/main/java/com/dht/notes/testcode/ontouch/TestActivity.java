package com.dht.notes.testcode.ontouch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dht.notes.R;
import com.dht.notes.testcode.ontouch.Recycler.RecycleActivity;

import java.util.ArrayList;

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

//        Log.d(TAG, "onCreate() returned:  getBaseContext() " +  getBaseContext());
//        Log.d(TAG, "onCreate() returned:  getApplicationContext() " +  getApplicationContext());
//        Log.d(TAG, "onCreate() returned:  getApplication() " +  getApplication());
//
//        Log.d(TAG, "onCreate() returned: Integer.MAX_VALUE " + Integer.MAX_VALUE);
//        Log.d(TAG, "onCreate() returned:TestActivity.this " + TestActivity.this);
//        Log.d(TAG, "onCreate() returned: ");

//        findViewById()
//        Log.d(TAG, "onCreate() returned: " + Thread.currentThread().getId());
//        System.out.println("android.os.Process.myPid()   = " + android.os.Process.myPid());
//        Log.d(TAG, "Looper.getMainLooper().getThread() " + Looper.getMainLooper().getThread().getId());
//
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "run: ");
//
//                System.out.println("android.os.Process.myPid()   = " + android.os.Process.myPid());
//            }
//        });
//        thread.start();
//        HandlerThread handlerThread = new HandlerThread("hkh");
//        Log.d(TAG, " thread.getId() returned: " + thread.getId());


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
        final ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            arrayList.add(i);
        }
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: test1");

                Task task = new Task();
                task.execute(arrayList);

            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: test2");
                Intent intent = new Intent(TestActivity.this, RecycleActivity.class);
                startActivity(intent);
            }
        });

//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        } ;


    }

    class Task extends AsyncTask<ArrayList<Integer>, Integer, Integer> {



        @Override
        protected Integer doInBackground(ArrayList<Integer>[] arrayLists) {
            Log.d(TAG, "doInBackground() returned: " + arrayLists[0]);
            for (int i = 0; i < arrayLists[0].size(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                publishProgress(i);
            }
            return arrayLists.length;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            test2.setText("初始化....");
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate() returned: " + values[0]) ;
            test2.setText("数据更新 " + values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG, "onPostExecute() returned: " + integer);
            test2.setText("数据更新 " + integer);
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
            Log.d(TAG, "onCancelled: " + integer);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG, "onCancelled: ");
        }
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
