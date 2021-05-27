package com.dht.notes.code;

import android.os.AsyncTask;
import android.os.Bundle;

public class MyAsyncTask extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Object object = new Object();
        String string = new String();
        string.trim();
    }

    @Override
    protected String doInBackground(String... strings) {
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
