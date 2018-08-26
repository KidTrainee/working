package com.example.binh.geofencing;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class GeoFenceTransitionsIntentService extends IntentService {

    public GeoFenceTransitionsIntentService() {
        super("IntentService");
    }

    public GeoFenceTransitionsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
