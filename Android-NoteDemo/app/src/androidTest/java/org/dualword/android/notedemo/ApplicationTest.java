package org.dualword.android.notedemo;

import android.test.ApplicationTestCase;

public class ApplicationTest extends ApplicationTestCase<NoteApp> {
    private NoteApp app;
    private IDb db;

    public ApplicationTest() {
        super(NoteApp.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        app = getApplication();
        db = app.getDb();
        assertNotNull(app);
        assertNotNull(db);
        db.resetDb();
        assertEquals("0 records in db", 0, db.getAll().getCount());
    }

    public void insertTest(){
        createNote();
        assertEquals("1 record in db", 1, db.getAll().getCount());
    }

    public void deleteTest(){
        createNote();
        assertEquals("1 record in db", 1, db.getAll().getCount());
        db.delete(1L);
        assertEquals("empty db", 0, db.getAll().getCount());
    }

    private void createNote(){
        Note note = new Note();
        note.setText("test");
        db.save(note);
    }


}