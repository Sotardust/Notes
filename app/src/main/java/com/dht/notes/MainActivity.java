package com.dht.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created by Administrator on 2019/5/28 18:46
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewStub viewStub ;

        Window window;

        Button button = (Button) findViewById(R.id.btn);
        Button button1 = (Button) findViewById(R.id.btn1);
        RxBus.getInstance().toObservable(BeanOne.class)
                .subscribe(new Observer<BeanOne>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BeanOne baseEvent) {

                        Log.d(TAG, "onNext: BeanOne");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        RxBus.getInstance().toObservable(BeanTwo.class)
                .subscribe(new Observer<BeanTwo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BeanTwo baseEvent) {

                        Log.d(TAG, "onNext: BeanTwo");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean1 bean1 = new Bean1();
                bean1.name = "按钮1";
                RxBus.getInstance().post(new BeanTwo());
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(new BeanOne());
            }
        });
    }


}
