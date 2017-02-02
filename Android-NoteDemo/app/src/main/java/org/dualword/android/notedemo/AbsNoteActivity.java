package org.dualword.android.notedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

abstract public class AbsNoteActivity extends Activity {
    protected NoteApp app;
    protected Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (NoteApp)getApplication();
        db = app.getDb();
        printLog("onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLog("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLog("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printLog("onStop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        printLog("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLog("onDestroy");

    }

    protected void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void printLog(String s){
        Log.d(getClass().getSimpleName(), s);
    }

}
