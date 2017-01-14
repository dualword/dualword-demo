package org.dualword.android.notedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Db {
    private boolean inMemory;
    private static final String DB_NAME = "notedemo";
    private static final int DB_VERSION = 3;
    private static final String DB_TABLE = "note";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TXT = "txt";

    private final Context ctx;
    private DBHelper helper;
    private SQLiteDatabase db;
    Cursor cursor;

    public Db(Context ctx, boolean inMemory) {
        this.ctx = ctx;
        this.inMemory = inMemory;
    }

    public void open() {
        if(inMemory){
            helper = new DBHelper(ctx, null, null, DB_VERSION);
        }else{
            helper = new DBHelper(ctx, DB_NAME, null, DB_VERSION);
        }
        db = helper.getWritableDatabase();
    }

    public void close() {
        if(cursor != null && !cursor.isClosed()) cursor.close();
        if (helper !=null) helper.close();
    }

    public Cursor getAll() {
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
            cursor = null;
        }
        cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public void save(Note n) {
        if(n.getId() == null){
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TXT, n.getText());
            long id = db.insert(DB_TABLE, null, cv);
            n.setId(id);
        }else{
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TXT, n.getText());
            db.update(DB_TABLE, cv, COLUMN_ID + " = " + n.getId(), null);
        }
    }

    public void delete(Long id) {
        db.delete(DB_TABLE, COLUMN_ID + " = " + id.toString(), null);
    }
    public void resetDb(){
        db.execSQL("DELETE FROM " + DB_TABLE + ";");
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table " + DB_TABLE + "(" +
                            COLUMN_ID + " integer primary key autoincrement, " +
                            COLUMN_TXT + " text" +
                            ");";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
            this.onCreate(db);
            Log.d(this.getClass().getSimpleName(), "onUpgrade:");
        }
    }
}