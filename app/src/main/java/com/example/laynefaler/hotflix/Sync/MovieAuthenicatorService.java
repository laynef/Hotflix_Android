package com.example.laynefaler.hotflix.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by laynefaler on 5/31/16.
 */
public class MovieAuthenicatorService extends Service {

    private MovieAuthenicator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new MovieAuthenicator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
