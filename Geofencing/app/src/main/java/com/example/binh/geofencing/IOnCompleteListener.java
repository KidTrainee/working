package com.example.binh.geofencing;

import android.content.Intent;

import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCompleteListener;

public interface IOnCompleteListener extends OnCompleteListener<LocationSettingsResponse> {

    void onRequestCheckResult(int resultCode, Intent data);
}
