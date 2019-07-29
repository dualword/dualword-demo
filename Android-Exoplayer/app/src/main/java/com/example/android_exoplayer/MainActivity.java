package com.example.android_exoplayer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(android.R.id.list);

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.VideoColumns.DATA };
        Cursor c = getContentResolver().query(uri, projection, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter( this, R.layout.list_item,c,
                new String[] {MediaStore.Video.Media._ID, MediaStore.Video.VideoColumns.DATA},
                new int[] {R.id.txtid,R.id.txt});

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getApplication(), PlayerActivity.class);
                intent.setData(Uri.parse( ((TextView)((ViewGroup)v).getChildAt(1)).getText().toString()) );
                startActivity(intent);
            }
        });
    }

}
