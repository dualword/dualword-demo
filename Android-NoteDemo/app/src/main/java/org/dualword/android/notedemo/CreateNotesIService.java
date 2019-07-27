package org.dualword.android.notedemo;

import android.content.Intent;

import de.greenrobot.event.EventBus;

public class CreateNotesIService extends AbsIService {

    public final static String INTENT_CREATE_NOTES = "notes_number";
    NonsenseGenerator generator = new NonsenseGenerator();

    public CreateNotesIService() {
        super("CreateNotesIService");
    }

    @Override
    protected void handleIntent(Intent intent) {
        if(intent.getAction().equals(NoteApp.ACTION_CREATE_DATA)){
            int num = intent.getIntExtra(INTENT_CREATE_NOTES,0);
            for (int i = 0; i < num; i++) {
                Note note = new Note();
                note.setText(generator.makeText(10));
                ((NoteApp)getApplication()).getDb().save(note);
            }
        }else if(intent.getAction().equals(NoteApp.ACTION_CREATE_NOTE)){
            Note note = (Note)intent.getSerializableExtra("org.dualword.android.notedemo.note");
            ((NoteApp)getApplication()).getDb().save(note);
        }else if(intent.getAction().equals(NoteApp.ACTION_DELETE)){
            ((NoteApp)getApplication()).getDb().resetDb();
        }else if(intent.getAction().equals(NoteApp.ACTION_DELETE_NOTE)){
            Note note = (Note)intent.getSerializableExtra("org.dualword.android.notedemo.note");
            ((NoteApp)getApplication()).getDb().delete(note);
        }

    }
}
