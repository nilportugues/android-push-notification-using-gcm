package net.simplifiedcoding.androidgcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;


public class GCMTokenRefreshListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
