package org.dualword.android.notedemo;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class NoteApp extends Application {
    public static String INTENT_BROADCAST = "intent_broadcast";
    public static String INTENT_REQUERY = "intent_requery";

    private Db db;
    private boolean inMemory;
    private AsyncTask task;
    private MainActivity activity;
    NonsenseGenerator generator = new NonsenseGenerator();

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

        startService(new Intent(this, NoteAppService.class));
         //        stopService(new Intent(this, NoteAppService.class));

        new BroadcastThread().start();
    }

    public void setActivity(MainActivity activity){
        this.activity = activity;

        if(task != null && !task.isCancelled()){
            this.activity.showProgress();
        }
    }

    public Db getDb(){
        return db;
    }

    @Override
    public void  onTrimMemory(int level) {
        Log.d(this.getClass().getSimpleName(), " onTrimMemory " + level);
        super.onTrimMemory(level);
    }

    public void createRecords(int num){
        task = new CreateNotesTask().execute(num);
    }

    private class CreateNotesTask extends AsyncTask<Integer, Integer, Long> {
        Long t = System.currentTimeMillis();
        @Override
        protected Long doInBackground(Integer...nums) {
            for (int i = 0; i < nums[0]; i++) {
                Note n = new Note();
                n.setText(generator.makeText(10));
                db.save(n);
            }
            return 0L;
        }
        @Override
        protected void onPreExecute() {
            activity.showProgress();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(Long result) {
            t = (System.currentTimeMillis() - t) / 1000;
            Toast.makeText(getApplicationContext(), "Creating test data: " + t + " sec.",
                    Toast.LENGTH_SHORT).show();
            activity.hideProgress();
            cancel(true);
        }
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

    private class BroadcastThread extends Thread{
        
        @Override
        public void run() {
            super.run();

            while (true){
                Intent localIntent = new Intent(NoteApp.INTENT_BROADCAST);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }

        }
    }

}