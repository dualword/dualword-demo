package org.dualword.android.notedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NoteEditorActivity extends AbsNoteActivity {
    private Note note;
    private TextView id;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        id = (TextView)findViewById(R.id.note_id);
        txt = (TextView)findViewById(R.id.note_text);

        if(getIntent() != null){
            note = (Note)getIntent().getSerializableExtra("org.dualword.android.notedemo.note");
            if(note != null){
                id.setText("Id:"+note.getId().toString());
                txt.setText(note.getText());
            }else{
                note = new Note();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_save:
                save();
                break;
            case R.id.menu_delete:
                delete();
                break;
            case android.R.id.home:
            case R.id.menu_cancel:
                cancel();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save() {
        printLog("save...");
        note.setText(txt.getText().toString());
        if(note.getText() != null && note.getText().trim().length() > 0){
            startService(NoteApp.ACTION_CREATE_NOTE);
        }
        finish();
    }

    public void delete() {
        printLog("delete...");
        if(note.getId() != null){
            startService(NoteApp.ACTION_DELETE_NOTE);
        }
        finish();
    }

    public void cancel() {
        printLog("cancel...");
        finish();
    }

    private void startService(String action){
        Intent intent = new Intent(this, CreateNotesIService.class);
        intent.setAction(action);
        intent.putExtra("org.dualword.android.notedemo.note", note);
        startService(intent);
    }

}
