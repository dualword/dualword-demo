package org.dualword.android.notedemo;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class NoteApp extends Application {
    public static String INTENT_REQUERY = "intent_requery";

    private IDb db;
    private boolean inMemory;
    private AsyncTask task;

    public NoteApp() {
        this(false);
    }

    public NoteApp(boolean inMemory) {
        this.inMemory = inMemory;
        db = new Db(this,inMemory);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(this.getClass().getSimpleName(), "onCreate");
        db.open();

        //startService(new Intent(this, NoteAppService.class));
         //stopService(new Intent(this, NoteAppService.class));

    }

    public IDb getDb(){
        return db;
    }

    @Override
    public void  onTrimMemory(int level) {
        Log.d(this.getClass().getSimpleName(), " onTrimMemory " + level);
        super.onTrimMemory(level);
    }

    public void createRecords(int num){
        Intent intent = new Intent(this, CreateNotesIService.class);
        intent.putExtra(CreateNotesIService.INTENT_CREATE_NOTES, num);
        startService(intent);
    }

    //    private boolean isServiceRunning(Class<?> cls) {
//        ActivityManager mgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo srv : mgr.getRunningServices(Integer.MAX_VALUE)) {
//            if (cls.getName().equals(srv.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }


}