package org.dualword.android.notedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import de.greenrobot.event.EventBus;

abstract class AbsIService extends IntentService {

    public AbsIService() {
        super(null);
    }

    public AbsIService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent == null) return;
        Long t = System.currentTimeMillis();
        handleIntent(intent);
        Log.d(this.getClass().getSimpleName(), intent.getAction() + " " + (System.currentTimeMillis() - t) + " ms.");
        EventBus.getDefault().post(new MessageEvent(intent.getAction() + " " + (System.currentTimeMillis() - t) + " ms."));
        EventBus.getDefault().post(new RefreshEvent());
    }

    abstract protected void handleIntent(Intent intent);

}
