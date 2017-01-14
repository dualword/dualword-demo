package org.dualword.android.notedemo;

import android.app.Application;
import android.util.Log;

public class NoteApp extends Application {
    private Db db;
    private boolean inMemory;

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

    public Db getDb(){
        return db;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(this.getClass().getSimpleName(), "onTerminate");
        db.close();
    }

    @Override
    public void  onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(this.getClass().getSimpleName()," onTrimMemory");
    }

}
