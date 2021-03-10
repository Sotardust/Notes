package com.dht.notes.code.other.thread;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.dht.notes.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by Administrator on 2019/9/25 17:56
 *
 * @author Administrator
 */
public class ThreadPoolExecutorTestActivity extends Activity {

    private static final String TAG = "dht";

    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1000,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(9), new ThreadFactory() {
        @Override
        public Thread newThread (Runnable r) {
            return new Thread(r);
        }
    });
    ThreadPoolExecutor service = new ThreadPoolExecutor(1, 5, 1000,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread (Runnable r) {
            return new Thread(r);
        }
    });

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Button button1 = findViewById(R.id.btn1);
        Button button2 = findViewById(R.id.btn2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                threadPool();
//                executor.
            }
        });

        Thread  thread = new Thread();
        Runnable runnable = new Runnable() {
            @Override
            public void run () {

            }
        };


        Object object = new Object();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                threadPool();
            }
        });
    }

    final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    final ThreadPoolExecutor executorService1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(3, new ThreadFactory() {

        AtomicInteger index = new AtomicInteger(1);

        @Override
        public Thread newThread (Runnable r) {
            return new Thread(r, "离线提交线程池-" + index.getAndIncrement());
        }
    });

    private void threadPool () {
        for (int i = 0; i < 10; i++) {

            final int finalI = i;

            executor.execute(new Runnable() {
                @Override
                public void run () {
                    try {
                        Thread.sleep(1000 * finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread thread = Thread.currentThread();

                    BlockingQueue<Runnable> runnables = executor.getQueue();
                    Log.d(TAG, "executor() called i = " + finalI + ", Thread = " + thread + ", runnables = " + runnables.size());
                }
            });

        }

        for (int i = 0; i < 10; i++) {

            final int finalI = i;
            service.execute(new Runnable() {
                @Override
                public void run () {
                    try {
                        Thread.sleep(1000 * finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Thread thread = Thread.currentThread();

                    BlockingQueue<Runnable> runnables = service.getQueue();
                    Log.d(TAG, "service() called i = " + finalI + ", Thread = " + thread + ", runnables = " + runnables.size());
                }
            });

        }
    }
}
