package org.dualword.android.notedemo;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.InputFilter;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import android.content.*;

import de.greenrobot.event.EventBus;

public class MainActivity extends AbsNoteActivity implements TextToSpeech.OnInitListener {
    private ListView mListView;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private SearchView searchView;
    //private TextToSpeech tts;

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

        String[] from = new String[] { Db.COLUMN_ID, Db.COLUMN_TXT };
        int[] to = new int[] { R.id.note_id, R.id.note_text };
        adapter = new SimpleCursorAdapter(this, R.layout.noteslist_item, cursor, from, to);
        mListView.setAdapter(adapter);

        //tts = new TextToSpeech(this, this);

    }

    @Override
    protected void onDestroy() {
//        if (tts != null) {
//            tts.stop();
//            tts.shutdown();
//        }

        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }
    }

    private void showResults(String query) {
        printLog("showResults:" + query);
        if(cursor != null)  {
            cursor.close();
            cursor = null;
        }
        cursor = db.search(query);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        if(cursor != null)  {
            cursor.close();
            cursor = null;
        }

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(cursor == null) cursor = db.getAll();
        showToast(cursor.getCount() + " note(s).");
        adapter.changeCursor(cursor);
        if(searchView != null && !searchView.isIconified()) searchView.setIconified(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
                cursor = db.getAll();
                showToast(cursor.getCount() + " note(s).");
                adapter.changeCursor(cursor);
                return false;
            }
        });

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
                        Intent intent = new Intent(getApplicationContext(), CreateNotesIService.class);
                        intent.setAction(NoteApp.ACTION_DELETE);
                        startService(intent);
                    }
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
        in.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
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
                Intent intent = new Intent(getApplicationContext(), CreateNotesIService.class);
                intent.setAction(NoteApp.ACTION_CREATE_DATA);
                intent.putExtra(CreateNotesIService.INTENT_CREATE_NOTES, j);
                startService(intent);
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

    private void refresh(){
        if (cursor != null) {
            cursor.requery();
            showToast(db.getCount() + " note(s) in database.");
        }
    }

    @Override
    public void onInit(int status) {
//        if (status == TextToSpeech.SUCCESS) {
//            int result = tts.setLanguage(Locale.US);
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                printLog("TTS:Language is not supported");
//            }else {
//                printLog("TTS:TTS init");
//                int res = tts.speak("hello", TextToSpeech.QUEUE_ADD, null);
//            }
//        } else {
//            printLog("TTS:onInit Failed");
//        }
    }

    public void onEventMainThread(MessageEvent event) {
        showToast(event.getMsg());
    };

    public void onEventMainThread(RefreshEvent event) {
        refresh();
    };

}
