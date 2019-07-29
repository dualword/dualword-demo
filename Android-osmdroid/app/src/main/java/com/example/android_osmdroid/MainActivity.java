package com.example.android_osmdroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import org.osmdroid.mapsforge.MapsForgeTileProvider;
import org.osmdroid.mapsforge.MapsForgeTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import java.io.File;

public class MainActivity extends Activity {
    MapView map = null;
    CompassOverlay mCompass;
    private String file = "world.map";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        MapsForgeTileSource.createInstance(getApplication());

        setContentView(R.layout.activity_main);
        map = (MapView) findViewById(R.id.map);
        map.setUseDataConnection(false);
        map.setClickable(true);

        File[] maps = {new File(Environment.getExternalStorageDirectory(), file)};
        MapsForgeTileSource files = MapsForgeTileSource.createFromFiles(maps);
        MapsForgeTileProvider forge = new MapsForgeTileProvider(new SimpleRegisterReceiver(ctx), files, null);
        map.setTileProvider(forge);

        mCompass = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
        mCompass.enableCompass();
        map.getOverlays().add(mCompass);

        final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBar = new ScaleBarOverlay(map);
        mScaleBar.setCentred(true);
        mScaleBar.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(mScaleBar);

    }

    public void onResume(){
        super.onResume();
        map.onResume();
    }

    public void onPause(){
        super.onPause();
        map.onPause();
    }

}
