package com.dht.notes.testcode.ontouch;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LruCache;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dht.notes.R;
import com.dht.notes.testcode.ontouch.recycler.RecycleActivity;
import com.dht.notes.testcode.ontouch.test.Test;
import com.jakewharton.disklrucache.DiskLruCache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    private volatile int num;


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
//        calculate();
//        MessageQueue
//        hist

        Test test = new Test();
        test.printNxN(3);

        Log.d(TAG, "onCreate() returned: getTaskId()" + getTaskId());

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


//        HandlerThread
//        WindowManager
//        ViewGroup
        Log.d(TAG, "onCreate: ");
        testRxJava();
        View view;
        ViewGroup viewGroup ;

    }
    public void testGlide(){
        DiskLruCache diskLruCache ;
        LruCache cache ;
        Handler handler ;
        Thread.currentThread();
//        HashMap

    }


    public void testRxJava() {
        // RxJava的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者(Observable) & 定义需发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);

                Thread.sleep(5000);
                emitter.onNext(2);
                Thread.sleep(1000);
                emitter.onNext(3);
                Thread.sleep(2000);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 2. 创建观察者(Observer) & 定义响应事件的行为
            // 3. 通过订阅（subscribe）连接观察者和被观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }

        });

    }

    public void testRxJava(final ObservableEmitter<Integer> emitter) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者(Observable) & 定义需发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);

                Thread.sleep(5000);
                emitter.onNext(212);
                Thread.sleep(1000);
                emitter.onNext(3);
                Thread.sleep(2000);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    // 2. 创建观察者(Observer) & 定义响应事件的行为
                    // 3. 通过订阅（subscribe）连接观察者和被观察者
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        emitter.onNext(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
//            HashMap
//            activity.test1.setText("fafdsa");
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
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        Toast.makeText(getApplicationContext(), "onTouchEvent", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //系统默认点击空白处 super.dispatchTouchEvent(ev)=false
        //系统默认点击按钮处 super.dispatchTouchEvent(ev)=true
        //必须执行super.dispatchTouchEvent(ev) 方法 否则即使返回true或者false 都不会往下传递也不会执行onTouchEvent()
        Log.d(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
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

    /**
     * 如何对两千万个不同的以137开始的手机号码进行排序
     */

    String[] strings = {"18222170916", "18226170916", "18222670616", "18222170916", "18223177916", "18222171916", "18222170816", "18222190916",};

    /**
     * 截取前三位数，然后转换后面几位数为整数型，使用快速排序
     */

    private void calculate() {
        Log.d(TAG, "calculate: ");
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("Integer.MAX_VALUE = " + "18222170916");

//        System.out.println("(\"18222170916\".\"18222370916\") = " + ("18222170916"."18222370916"));
//        Arrays.sort();

    }
}
