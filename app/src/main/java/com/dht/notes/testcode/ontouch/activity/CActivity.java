package com.dht.notes.testcode.ontouch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.dht.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dai on 2018/4/23.
 */

public class CActivity extends Activity {

    private static final String TAG = "CActivity";
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_c);
        Log.d(TAG, "onCreate: ");
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn)
    public void onViewClicked () {
        Intent intent = new Intent(CActivity.this, DActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");

    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart () {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume () {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStart () {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onPause () {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop () {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

}
