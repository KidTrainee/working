package com.example.binh.geofencing;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LastKnownLocationService {

    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 1000;
    private final AppCompatActivity mActivity;
    private FusedLocationProviderClient fusedLocationClient;
    private final OnSuccessListener<Location> mOnGetLastKnownLocationListener;

    public LastKnownLocationService(AppCompatActivity activity, OnSuccessListener<Location> onGetLastKnownLocationListener) {
        mActivity = activity;
        mOnGetLastKnownLocationListener = onGetLastKnownLocationListener;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        // Here, thisActivity is the current activity
        if (checkLocationPermissions()) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showAlertDialog();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ACCESS_FINE_LOCATION);

                // REQUEST_CODE_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            getLastLocation();
        }
    }

    private boolean checkLocationPermissions() {
        return ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    }


    private void getLastLocation() {
        // Permission has already been granted
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(mOnGetLastKnownLocationListener);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(mActivity)
                .setMessage("Permission is required")
                .show();
    }

    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        boolean allGranted = true;
        for (int result : grantResults) {
            if (result != Activity.RESULT_OK) allGranted = false;
        }
        if (allGranted) getLastLocation();
    }
}
