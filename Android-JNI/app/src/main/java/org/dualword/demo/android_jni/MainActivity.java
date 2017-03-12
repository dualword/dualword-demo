package org.dualword.demo.android_jni;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView txt;

    static {
        System.loadLibrary("testjni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);

        txt.setText(jniString());

    }

    public native String jniString();

}
