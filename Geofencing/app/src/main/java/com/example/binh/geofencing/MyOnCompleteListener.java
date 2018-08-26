package com.example.binh.geofencing;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyOnCompleteListener implements IOnCompleteListener {

    private AppCompatActivity mActivity;

    public MyOnCompleteListener(AppCompatActivity activity) {
        mActivity = activity;
    }

    static final int REQUEST_CHECK_SETTINGS = 1001;

    /**
     * When the Task completes, the client can check the location settings by looking at
     * the status code from the LocationSettingsResponse object.
     * The client can also retrieve the current state of the relevant location settings
     * by calling getLocationSettingsStates():
     * @param task
     */
    @Override
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
        try {
            LocationSettingsResponse response = task.getResult(ApiException.class);
            // All location settings are satisfied. The client can initialize location
            // requests here.
            createLocationRequest();
        } catch (ApiException exception) {
            switch (exception.getStatusCode()) {
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the
                    // user a dialog.
                    try {
                        // Cast to a resolvable exception.
                        ResolvableApiException resolvable = (ResolvableApiException) exception;
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        resolvable.startResolutionForResult(
                                mActivity, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    } catch (ClassCastException e) {
                        // Ignore, should be an impossible error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.

                    break;
            }
        }
    }

    private void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        // setPriority() - This method sets the priority of the request, which gives which location sources to use. The following values are supported:
        // PRIORITY_BALANCED_POWER_ACCURACY - Use this setting to request location precision to within a city block, which is an accuracy of approximately 100 meters. This is considered a coarse level of accuracy, and is likely to consume less power. With this setting, the location services are likely to use WiFi and cell tower positioning. Note, however, that the choice of location provider depends on many other factors, such as which sources are available.
        // PRIORITY_HIGH_ACCURACY - Use this setting to request the most precise location possible. With this setting, the location services are more likely to use GPS to determine the location.
        // PRIORITY_LOW_POWER - Use this setting to request city-level precision, which is an accuracy of approximately 10 kilometers. This is considered a coarse level of accuracy, and is likely to consume less power.
        // PRIORITY_NO_POWER - Use this setting if you need negligible impact on power consumption, but want to receive location updates when available.  With this setting, your app does not trigger any location updates, but receives locations triggered by other apps.
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onRequestCheckResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                // All required changes were successfully made

                break;
            case Activity.RESULT_CANCELED:
                // The user was asked to change settings, but chose not to

                break;
            default:
                break;
        }
    }
}
