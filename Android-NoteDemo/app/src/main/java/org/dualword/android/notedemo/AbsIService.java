package org.dualword.android.notedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

abstract class AbsIService extends IntentService {

    public AbsIService() {
        super(null);
    }

    public AbsIService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent");
        if(intent == null) return;
        handleIntent(intent);
        Intent localIntent = new Intent(NoteApp.INTENT_REQUERY);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);
    }

    abstract protected void handleIntent(Intent intent);

}
