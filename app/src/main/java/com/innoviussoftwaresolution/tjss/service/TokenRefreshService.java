package com.innoviussoftwaresolution.tjss.service;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class TokenRefreshService extends IntentService {


    public TokenRefreshService() {
        super("TokenRefreshService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
