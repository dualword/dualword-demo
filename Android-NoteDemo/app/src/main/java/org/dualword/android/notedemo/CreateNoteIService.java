package org.dualword.android.notedemo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by alex on 2/3/17.
 */
public class CreateNoteIService extends IntentService {

    public CreateNoteIService() {
        super(null);
    }

    public CreateNoteIService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent");
        if(intent == null) return;

        Note note = (Note)intent.getSerializableExtra("org.dualword.android.notedemo.note");
        ((NoteApp)getApplication()).getDb().save(note);

        Notification.Builder mBuilder = new Notification.Builder(getApplication().getApplicationContext());
        Notification n = mBuilder.setContentTitle("New note")
                .setSmallIcon(R.drawable.live_folder_notes)
                .setContentText("New note created.")
                .getNotification();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, n);

        Intent localIntent = new Intent(NoteApp.INTENT_REQUERY);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);

    }
}
