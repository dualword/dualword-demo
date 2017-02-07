package org.dualword.android.notedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by alex on 2/2/17.
 */
public class NoteAppService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(this.getClass().getSimpleName(), "onStartCommand");
        handleCommand(intent);
        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(this.getClass().getSimpleName(), "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName(), "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(this.getClass().getSimpleName(), "onBind");
        return null;
    }

    private void handleCommand(Intent intent) {

    }



}
