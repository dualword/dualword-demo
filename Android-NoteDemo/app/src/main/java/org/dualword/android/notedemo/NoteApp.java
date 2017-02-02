package org.dualword.android.notedemo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class NoteApp extends Application {
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

}