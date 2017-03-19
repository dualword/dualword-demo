package org.dualword.demo.android_lucene;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {
    private TextView tv;
    private String field = "text";
    private Analyzer analyzer;
    private Directory dir;
    private IndexWriterConfig iwc;
    private IndexReader reader;
    private IndexWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.txt);

        File f = new File(getFilesDir().getAbsolutePath() + File.separator + "index");
        tv.append("Index:" + f.getAbsolutePath().toString() + "\n\n");

        int docnum = 0;
        try {
            init(f);
            docnum = writer.numDocs();
            tv.append("Documents:"+ docnum + "\n\n");
            if(docnum == 0){
                index();
            }
            search("test");
            writer.close();
        } catch (IOException e) {
            Log.d(getClass().getSimpleName(), e.getMessage());
        }
    }

    private void init(File file) throws IOException {
        analyzer = new StandardAnalyzer(Version.LUCENE_36);
        dir = FSDirectory.open(file);
        iwc = new IndexWriterConfig(Version.LUCENE_36, analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(dir, iwc);
    }

    private void index() throws IOException {
        String text = "test";
        tv.append("Indexing:"+text+"\n\n");
        if(text==null) return;
        Document doc = new Document();
        Field pathField = new Field(field, text, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
        doc.add(pathField);
        writer.addDocument(doc);
        writer.commit();
        tv.append("Documents:" + writer.numDocs() + "\n\n");
    }

    private void search(String str) throws IOException {
        tv.append("Searching:"+str+"\n\n");
        reader = IndexReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser(Version.LUCENE_36, field, analyzer);
        Query query = null;
        try {
            query = parser.parse(str);
        } catch (ParseException e) {
//            /e.printStackTrace();
        }
        TopDocs results = searcher.search(query, 100);
        tv.append("Hits:" + results.totalHits + "\n\n");
        reader.close();

    }

}
