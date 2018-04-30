package org.dualword.testgps;

import android.app.Activity;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements GpsStatus.Listener, LocationListener {
    private LocationManager gpsManager;
    private SatView satView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        satView = (SatView) (findViewById(R.id.satView));
        gpsManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsManager.removeGpsStatusListener(this);
        try {
            gpsManager.removeUpdates(this);
        }catch (SecurityException e){
            Log.e("SecurityException:", e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            LocationProvider mProvider = gpsManager.getProvider(LocationManager.GPS_PROVIDER);
            if (mProvider == null) {
                Log.e(getClass().getSimpleName(), "Unable to get GPS_PROVIDER");
                return;
            }
            gpsManager.addGpsStatusListener(this);
            gpsManager.requestLocationUpdates(mProvider.getName(), 0, 0, this);
        }catch (SecurityException e){
            Log.e("SecurityException:", e.getMessage());
        }

    }

    @Override
    public void onGpsStatusChanged(int event) {
        GpsStatus status = gpsManager.getGpsStatus(null);
        if(event == GpsStatus.GPS_EVENT_SATELLITE_STATUS){
            //Log.d("onGpsStatusChanged",event+"");
            if(status != null) {
                satView.setStatus(status);
                satView.invalidate();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.d("onLocationChanged", location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Log.d("onStatusChanged", provider + " status:" + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Log.d("onProviderEnabled", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Log.d("onProviderDisabled", provider);
    }
}
