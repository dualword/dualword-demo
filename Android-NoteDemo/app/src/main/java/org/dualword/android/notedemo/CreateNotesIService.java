package org.dualword.android.notedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class CreateNotesIService extends AbsIService {

    public final static String INTENT_CREATE_NOTES = "notes_number";
    NonsenseGenerator generator = new NonsenseGenerator();

    @Override
    protected void handleIntent(Intent intent) {
        Long t = System.currentTimeMillis();

        Notification.Builder mBuilder = new Notification.Builder(getApplication().getApplicationContext());
        Notification n = mBuilder.setContentTitle("New notes")
                .setSmallIcon(R.drawable.live_folder_notes)
                .setContentText("Creating notes ...").getNotification();
        startForeground(1, n);

        int num = intent.getIntExtra(INTENT_CREATE_NOTES,0);
        for (int i = 0; i < num; i++) {
            Note note = new Note();
            note.setText(generator.makeText(10));
            ((NoteApp)getApplication()).getDb().save(note);
        }

        stopForeground(true);

        t = (System.currentTimeMillis() - t) / 1000;
        mBuilder = new Notification.Builder(getApplication().getApplicationContext());
        n = mBuilder.setContentTitle("New notes")
                .setSmallIcon(R.drawable.live_folder_notes)
                .setContentText(num + " notes created (" + t + "sec.)")
                .getNotification();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, n);

    }
}
