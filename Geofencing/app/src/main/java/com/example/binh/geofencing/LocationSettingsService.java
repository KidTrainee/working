package com.example.binh.geofencing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

class LocationSettingsService {

    private LocationRequest mLocationRequestHighAccuracy, mLocationRequestBalancedPowerAccuracy;
    private IOnCompleteListener mOnCompleteListener;

    public LocationSettingsService(AppCompatActivity activity) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequestHighAccuracy)
                .addLocationRequest(mLocationRequestBalancedPowerAccuracy);
        builder.setNeedBle(true);
        // Then check whether current location settings are satisfied:
        SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> result = settingsClient.checkLocationSettings(builder.build());

        mOnCompleteListener = new MyOnCompleteListener(activity);
        result.addOnCompleteListener(mOnCompleteListener);
    }

    public void onRequestCheckResult(int resultCode, Intent data) {
        mOnCompleteListener.onRequestCheckResult(resultCode, data);
    }
}
