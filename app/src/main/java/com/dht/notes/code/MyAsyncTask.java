package com.dht.notes.code;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyAsyncTask extends AsyncTask<String, String, String> {

    private static final String TAG = "MyAsyncTask";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "doInBackground: ");
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }
}
