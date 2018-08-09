package com.scorematics.android.fcm;/*

package com.saavi.android.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.saavi.android.utils.PreferenceHandler;


public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";


    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "FCM Registration Token: " + token);
        PreferenceHandler.writeString(getApplicationContext(), PreferenceHandler.DEVICE_TOKEN,token);
    }
}
*/
