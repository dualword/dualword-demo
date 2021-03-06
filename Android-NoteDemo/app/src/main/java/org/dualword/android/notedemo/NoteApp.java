package org.dualword.android.notedemo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

public class NoteApp extends Application {
    public static String ACTION_CREATE_DATA = "create_data";
    public static String ACTION_CREATE_NOTE = "create_note";
    public static String ACTION_DELETE = "delete";
    public static String ACTION_DELETE_NOTE = "delete_note";

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
    }

    public IDb getDb(){
        return db;
    }

    @Override
    public void  onTrimMemory(int level) {
        Log.d(this.getClass().getSimpleName(), " onTrimMemory " + level);
        super.onTrimMemory(level);
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