package com.dht.notes.testcode.ontouch;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dht.notes.R;
import com.dht.notes.testcode.ontouch.recycler.RecycleActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/2/8.
 */

public class TestActivity extends Activity {

    private static final String TAG = "TestActivity";
    //    @BindView(R.id.notes)
//    MyTextView notes;
    @BindView(R.id.linearLayout)
    MyLinearLayout linearLayout;
    @BindView(R.id.test1)
    MyTextView test1;
    @BindView(R.id.test2)
    TextView test2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Intent intent = new Intent();
        final ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            arrayList.add(i);
        }
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
                Intent intent = new Intent(TestActivity.this, RecycleActivity.class);
                startActivity(intent);
            }
        });
        MyHandler myHandler = new MyHandler(this);

        Log.d(TAG, "onCreate: ");

    }

    static class MyHandler extends Handler {
        WeakReference<Activity> reference;


        MyHandler(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TestActivity activity = (TestActivity) reference.get();
            activity.test1.setText("fafdsa");
        }
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
            Log.d(TAG, "onProgressUpdate() returned: " + values[0]);
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
        //系统默认super.onTouchEvent(event) = false
        //无论true或者false 都不会处理
        Log.d(TAG, "onTouchEvent: " );
        Toast.makeText(getApplicationContext(),"onTouchEvent",Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //系统默认点击空白处 super.dispatchTouchEvent(ev)=false
        //系统默认点击按钮处 super.dispatchTouchEvent(ev)=true
        //必须执行super.dispatchTouchEvent(ev) 方法 否则即使返回true或者false 都不会往下传递也不会执行onTouchEvent()
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume: ");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart: ");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause: ");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(TAG, "onRestart: ");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy: ");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop: ");
//    }
}
