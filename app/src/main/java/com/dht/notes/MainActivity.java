package com.dht.notes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created by Administrator on 2019/5/28 18:46
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button button = (Button) findViewById(R.id.btn);
        Button button1 = (Button) findViewById(R.id.btn1);
        RxBus.getInstance().toObservable(BeanOne.class)
                .subscribe(new Observer<BeanOne>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BeanOne baseEvent) {

                        Log.d(TAG, "onNext: BeanOne" );
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

                        Log.d(TAG, "onNext: BeanTwo" );
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
