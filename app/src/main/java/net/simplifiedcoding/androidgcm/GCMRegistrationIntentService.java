package net.simplifiedcoding.androidgcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class GCMRegistrationIntentService extends IntentService {

    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GCMRegistrationIntentService() {
        super(GCMRegistrationIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM(); //Registering gcm to the device!
    }

    private void registerGCM() {

        Intent registrationComplete = new Intent();

        try {
            String deviceToken = obtainDeviceToken();
            registrationComplete.setAction(REGISTRATION_SUCCESS);   //on registration complete creating intent with success
            registrationComplete.putExtra("token", deviceToken);    //Putting the token to the intent

        } catch (Exception e) {
            registrationComplete.setAction(REGISTRATION_ERROR);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private String obtainDeviceToken() throws IOException {

        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());

        String deviceToken = instanceID.getToken(
                getString(R.string.gcm_defaultSenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                null
        );

        Log.w("GCMRegIntentService", "token:" + deviceToken);

        return deviceToken;
    }
}
