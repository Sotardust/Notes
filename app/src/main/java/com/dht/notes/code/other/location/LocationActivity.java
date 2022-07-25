package com.dht.notes.code.other.location;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.dht.notes.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by Administrator on 2019/10/21 15:08
 */
public class LocationActivity extends Activity {

    private static final String TAG = "dht";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        GasAndLocationUtil.INSTANCE.init(this);
        GasAndLocationUtil.INSTANCE.startUploadData(this);
        TextView start = findViewById(R.id.start);
        final TextView stop = findViewById(R.id.stop);

        ArrayList<String> list = new ArrayList<>(7);
        list.add("f1");
        list.add("f2");
        list.add("f3");
        list.add("f4");
        list.add("f5");

        List<String> subList =list.subList(0,2);
        subList.add("fdsa");
        list.add("fdas");
        subList.add("fdsa");
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("fdas");


        final OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.1.84:8090/Interface/getEditionInfo";
        final Request request = new Request.Builder()
                .url(url)
                .build();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GasAndLocationUtil.INSTANCE.startUploadData(LocationActivity.this);
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = client.newCall(request).execute();
                            String value = response.body().string();
                            Log.d(TAG, "onClick() called with: value = [" + value + "]");
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: e", e);
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GasAndLocationUtil.INSTANCE.stopUploadData();
            }
        });


    }
}
