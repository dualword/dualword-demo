package org.dualword.android.notedemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AbsNoteActivity {
    private ListView mListView;
    private Cursor cursor;
    private ProgressDialog loading;

    NonsenseGenerator generator = new NonsenseGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list);

        EditText txt = new EditText(this);
        txt.setText("No data.");
        txt.setEnabled(false);
        mListView.setEmptyView(txt);
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(txt);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ViewGroup vg = (ViewGroup) v;
                TextView t1 = (TextView) vg.getChildAt(0);
                TextView t2 = (TextView) vg.getChildAt(1);
                Intent intent = new Intent(getApplication(), NoteEditorActivity.class);
                Note o = new Note();
                o.setId(Integer.valueOf(t1.getText().toString()));
                o.setText(t2.getText().toString());
                intent.putExtra("org.dualword.android.notedemo.note", o);
                startActivity(intent);
            }
        });

        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("In progress...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cursor != null)  {
            cursor.close();
            cursor = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cursor = db.getAll();
        printLog("rows:"+cursor.getCount());

        String[] from = new String[] { Db.COLUMN_ID, Db.COLUMN_TXT };
        int[] to = new int[] { R.id.note_id, R.id.note_text };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.noteslist_item, cursor, from, to);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add){
            startActivity(new Intent(this, NoteEditorActivity.class));
            return true;
        }else if (id == R.id.action_testdata) {
            createTestData();
            return true;
        }else if (id == R.id.action_resetdb) {
            resetDb();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetDb(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete All records?");
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int btn) {
            db.resetDb();
            cursor.requery();  }
        } );
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int btn) {
            // Canceled.
                    }
        } );
        alert.show();
    }

    private void createTestData(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Create Notes");
        alert.setMessage("How many to create?");
        final EditText in = new EditText(this);
        in.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        in.setInputType(InputType.TYPE_CLASS_NUMBER);
        in.setGravity(Gravity.CENTER_HORIZONTAL);
        alert.setView(in);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int btn) {
                String val = in.getText().toString();
                int j = 0;
                try {
                    j = Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    return;
                }
                new CreateNotesTask().execute(j);
            }
        }
        );
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int btn) {
                // Canceled.
            }
        }
        );
        alert.show();
    }

    private class CreateNotesTask extends AsyncTask<Integer, Integer, Long> {
        Long t = System.currentTimeMillis();
        @Override
        protected Long doInBackground(Integer...nums) {
            for (int i = 0; i < nums[0]; i++) {
                Note n = new Note();
                n.setText(generator.makeText(10));
                db.save(n);
            }
            return 0L;
        }
        @Override
        protected void onPreExecute() {
            loading.show();
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }
        @Override
        protected void onPostExecute(Long result) {
            t = (System.currentTimeMillis() - t) / 1000;
            Toast.makeText(getApplicationContext(), "Creating test data: " + t + " sec.",
                    Toast.LENGTH_SHORT).show();
            cursor.requery();
            loading.dismiss();
        }
    }

}
