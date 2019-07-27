package org.dualword.android.notedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

abstract public class AbsNoteActivity extends Activity {
    protected NoteApp app;
    protected IDb db;

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
        printLog("onResume ");
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
        printLog("onDestroy");
        super.onDestroy();
    }

    protected void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void printLog(String s){
        Log.d(getClass().getSimpleName(), s);
    }

}
