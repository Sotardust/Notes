package com.dht.notes.testcode.ontouch.Recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dht.notes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dai on 2018/3/27.
 */

public class RecycleActivity extends AppCompatActivity {

    @BindView(R.id.recycle)
    RecyclerView recycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_recycle);
        ButterKnife.bind(this);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        MyAdapter myAdapter = new MyAdapter() ;
        myAdapter.setList(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(myAdapter);
//        PhoneWindow
    }
}
