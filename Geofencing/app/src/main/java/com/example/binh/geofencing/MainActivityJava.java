package com.example.binh.geofencing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.example.binh.geofencing.LastKnownLocationService.REQUEST_CODE_ACCESS_FINE_LOCATION;
import static com.example.binh.geofencing.MyOnCompleteListener.REQUEST_CHECK_SETTINGS;

public class MainActivityJava extends AppCompatActivity implements OnSuccessListener<Location> {

    private Location mLocation;

    TextView mLocationTV;
    private LastKnownLocationService mLastKnownLocationService;
    private LocationSettingsService mLocationSettingsService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationTV = findViewById(R.id.text);
        mLocationSettingsService = new LocationSettingsService(this);
        mLastKnownLocationService = new LastKnownLocationService(this, this);
    }

    private void showLocation() {
        mLocationTV.setText(String.format(getString(R.string.location_format), mLocation.getLatitude(), mLocation.getLongitude()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                mLocationSettingsService.onRequestCheckResult(resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_FINE_LOCATION:
                mLastKnownLocationService.onRequestPermissionsResult(permissions, grantResults);
                break;
        }
    }

    /**
     * Get last known location success
     * @param location device's last known location
     */
    @Override
    public void onSuccess(Location location) {
        mLocation = location;
        showLocation();
    }
}
