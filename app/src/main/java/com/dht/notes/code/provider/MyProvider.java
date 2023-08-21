package com.dht.notes.code.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


public class MyProvider extends ContentProvider {

    private static final String TAG = "dht1";
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        matcher.addURI("com.dht.notes.code.provider", "user", 0);
//如果match()方法匹配content://cn.scu.myprovider/user路径，返回匹配码为USER
        matcher.addURI("com.dht.notes.code.provider", "user/#", 1);


        Log.d(TAG, "onCreate() called");
        return true;
    }

    @Nullable

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String value = uri.getQueryParameter("姓名");

        Log.d(TAG, "query() called with: getQueryParameterNames = [" + uri.getQueryParameterNames() + "],  uri = [" + uri + "], projection = [" + projection + "], selection = [" + selection + "], selectionArgs = [" + selectionArgs + "], sortOrder = [" + sortOrder + "]");
        return null;
    }

    @Nullable

    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType() called with: uri = [" + uri + "]");
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri.Builder builder = uri.buildUpon();
        if (values.keySet() !=null){
            for (String value:values.keySet()  ){
                builder.appendQueryParameter(value, String.valueOf(values.get(value)));
            }
        }

        Uri uri1 = builder.build();
        Log.d(TAG, "insert() called with: uri1 = [" + uri1.getQueryParameterNames() + "], uri = [" + uri + "], values = [" + values + "]");
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete() called with: uri = [" + uri + "], selection = [" + selection + "], selectionArgs = [" + selectionArgs + "]");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update() called with: uri = [" + uri.buildUpon().build() + "], values = [" + values + "], selection = [" + selection + "], selectionArgs = [" + selectionArgs + "]");
        return 0;
    }
}
