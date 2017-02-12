package org.dualword.android.notedemo;

import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.content.LocalBroadcastManager;

public class CreateNoteIService extends AbsIService {

    @Override
    protected void handleIntent(Intent intent) {

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

    }
}
