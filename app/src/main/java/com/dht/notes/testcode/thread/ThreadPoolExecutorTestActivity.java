package com.dht.notes.testcode.thread;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dht.notes.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by Administrator on 2019/9/25 17:56
 */
public class ThreadPoolExecutorTestActivity extends Activity {

    private static final String TAG = "dht";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Button button1 = findViewById(R.id.btn1);
        Button button2 = findViewById(R.id.btn2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPool();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPool();
            }
        });
    }

    private void threadPool() {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {

            final int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "executor called  i = " + finalI + ", Thread = " + Thread.currentThread());
                }
            });

        }
        final ExecutorService executorService1 = Executors.newFixedThreadPool(3, new ThreadFactory() {

            AtomicInteger index = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "离线提交线程池-" + index.getAndIncrement());
            }
        });
        for (int i = 0; i < 10; i++) {

            final int finalI = i;
            executorService1.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000 * finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "Service() called i = " + finalI + ", Thread = " + Thread.currentThread());
                }
            });

        }
    }
}
