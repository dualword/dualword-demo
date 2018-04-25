package org.dualword.demo.democamera;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreview = new CameraPreview(this);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        ImageButton captureButton = (ImageButton) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);

                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCamera = Camera.open();
            mPreview.setCamera(mCamera);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void rescan(String f) {
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        i.setData(Uri.fromFile(new File(f)));
        getApplicationContext().sendBroadcast(i);
    }

    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            //
        }
    };

    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            //
        }
    };

    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            new TakePictureTask().execute(data);
            mCamera.startPreview();
        }
    };

    private class TakePictureTask extends AsyncTask<byte[], Void, String> {

        protected String doInBackground(byte[]... data) {
            FileOutputStream fos = null;
            File outFile = null;
            try {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String fileName = String.format("IMG_%d.jpg", System.currentTimeMillis());
                outFile = new File(path.getAbsolutePath(), fileName);
                fos = new FileOutputStream(outFile);
                fos.write(data[0]);
                fos.flush();
                fos.close();
                rescan(outFile.getAbsolutePath());
            }catch (IOException e) {
                e.printStackTrace();
            }
            return outFile.getAbsolutePath();
        }

        protected void onPostExecute(String f) {
            Log.d("New Image saved:", f);
            Toast.makeText(getApplicationContext(), "New Image saved:" + f, Toast.LENGTH_SHORT).show();
        }
    }

}