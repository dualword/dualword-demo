package org.dualword.android.notedemo;

import android.database.Cursor;

public interface IDb {

    void open();

    void close();

    void save(Note n);

    void delete(Note n);

    void delete(Long id);

    Cursor getAll();

    int getCount();

    Cursor search(String query);

    void resetDb();
}
