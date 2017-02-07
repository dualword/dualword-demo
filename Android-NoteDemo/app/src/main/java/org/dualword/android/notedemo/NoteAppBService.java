package org.dualword.android.notedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by alex on 2/2/17.
 */
public class NoteAppBService extends Service{

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        NoteAppBService getService() {
            return NoteAppBService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(this.getClass().getSimpleName(), "onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(this.getClass().getSimpleName(), "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(this.getClass().getSimpleName(), "onRebind");
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

    public void test(){
        Log.d(this.getClass().getSimpleName(), "test");
    }
}
