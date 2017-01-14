package org.dualword.android.notedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

abstract public class AbsNoteActivity extends Activity {
    protected Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = ((NoteApp)getApplication()).getDb();
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

    protected void printLog(String s){
        Log.d(getClass().getSimpleName(), s);
    }

}
