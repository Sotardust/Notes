package com.dht.notes.testcode.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * created by Administrator on 2019/10/21 11:09
 */
public enum GasAndLocationUtil {

    INSTANCE;

    private static final String TAG = "dht";


    private ScheduledExecutorService service;

    private static final int DALAY_TIME = 5;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init(Activity activity) {
        LocationManager manager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        service = Executors.newSingleThreadScheduledExecutor();
        if (manager == null) return;
        String provider = manager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissions, 1);
            return;
        }

        manager.requestLocationUpdates(provider, 0, 0, ll);
    }

    private double latitude = 0; //纬度
    private double longitude = 0; //经度度
    private static final String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private LocationListener ll = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.d(TAG, "onLocationChanged() called with: location = [" + location + "]");
            Log.d(TAG, "local 纬度 lat = [" + location.getLatitude() + "]");
            Log.d(TAG, "local 经度 lng = [" + location.getLongitude() + "]");
        }

        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            Log.d(TAG, "onStatusChanged() called with: provider = [" + provider + "], status = [" + status + "], extras = [" + extras + "]");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled() called with: provider = [" + provider + "]");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled() called with: provider = [" + provider + "]");
        }

    };


    /**
     * 开始上传数据
     */
    public void startUploadData(final Activity activity) {
        if (service == null) {
            service = Executors.newSingleThreadScheduledExecutor();
        }
        service.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                try {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, permissions, 1);
                        return;
                    }

                    if (longitude == 0) return;
                    Log.d(TAG, "run() called  latitude = " + latitude + ", longitude= " + longitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, 0, DALAY_TIME, TimeUnit.SECONDS);

    }

    public void stopUploadData() {
        if (service != null) {
            service.shutdownNow();
            service = null;
        }
    }

}
